package pay.my.buddy.web;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pay.my.buddy.entities.Client;
import pay.my.buddy.service.IClientMetier;

/**
 * Implementing the management of interactions between the application client
 * and the application.
 *
 */
@Controller
public class ClientController {
	private static final Logger logger = LogManager.getLogger("ClientController");

	/**
	 * Calling operations of the client service
	 */
	@Autowired
	private IClientMetier clientMetier;

	/**
	 * Read-Get one client by id
	 * 
	 * @param idClient : id of the client we want to get his information
	 * @param model    : Model defines a holder for client attributes and is
	 *                 primarily designed for adding attributes to the model
	 * @return a client page web
	 * @throws Exception
	 */
	@RequestMapping(value = "/Profil", method = RequestMethod.GET)
	public String getClientById(Long idClient, Model model) {
		logger.info("Getting client by id");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String clientEmail = authentication.getName();

		try {
			Client clientFound = clientMetier.findByEmail(clientEmail);
			idClient = clientFound.getIdClient();
			model.addAttribute("idClient", idClient);
			Client client = clientMetier.findById(idClient);
			List<Client> connectionsList = client.getConnections();
			model.addAttribute("client", client);
			model.addAttribute("connectionsList", connectionsList);

		} catch (Exception e) {
			model.addAttribute("exception", e);
		}

		return "clients/getClient";
	}

	/**
	 * show Add Client Page ** this method allows to show a form which we fill the
	 * client information
	 * 
	 * @param model : Model defines a holder for client attributes and is primarily
	 *              designed for adding attributes to the model
	 * @return a saveClient page web
	 * @throws Exception
	 */
	@RequestMapping(value = { "/saveClient" }, method = RequestMethod.GET)
	public String showAddClientPage(Model model) {
		Client client = new Client();
		model.addAttribute("client", client);
		return "clients/addClient";
	}

	/**
	 * save client ** this method allows to save a client information which we have
	 * fill in the form
	 * 
	 * @param model  : Model defines a holder for client attributes and is primarily
	 *               designed for adding attributes to the model
	 * @param client : client object filling in the form
	 * @param result : BindingResult holds the result of a validation and binding
	 *               and contains errors that may have occurred.
	 * 
	 * @return a saveClient page web
	 * @throws Exception
	 */
	@RequestMapping(value = { "/saveClient" }, method = RequestMethod.POST)
	public String saveClient(Model model, @Valid @ModelAttribute("client") Client client, BindingResult result) {
		if (result.hasErrors()) {
			return "clients/addClient";
		}
		try {
			clientMetier.addNewClient(client);
			return "redirect:/profil?id=" + client.getIdClient();
		} catch (Exception e) {
			model.addAttribute("exception", e);
			return "clients/addClient";
		}

	}

	/**
	 * save connection in client page web ** this method allows to save a connection
	 * for a client
	 * 
	 * @param model    : Model defines a holder for client attributes and is
	 *                 primarily designed for adding attributes to the model
	 * @param idClient : id of the current client
	 * @param email    : email of the connection to add
	 * 
	 * @return a saveClient page web
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveConnection", method = RequestMethod.POST)
	public String saveConnection(Model model, String email, Long idClient) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String clientEmail = authentication.getName();
			Client clientFound = clientMetier.findByEmail(clientEmail);
			idClient = clientFound.getIdClient();
			clientMetier.updateListConnection(idClient, email);
			String info = "Success adding connection of :" + email;
			model.addAttribute("info", info);
		} catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/Profil?idClient=" + idClient + "&error=" + e.getMessage();
		}

		return "redirect:/Profil";

	}

	/**
	 * save connection in compte page web ** this method allows to save a connection
	 * for a client
	 * 
	 * @param model    : Model defines a holder for client attributes and is
	 *                 primarily designed for adding attributes to the model
	 * @param idClient : id of the current client
	 * @param email    : email of the connection to add
	 * 
	 * @return a saveClient page web
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveConnectionCompte", method = RequestMethod.POST)
	public String saveConnectionCompte(Model model, String email, Long idClient, Long idCompte) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String clientEmail = authentication.getName();
		idCompte = clientMetier.findByEmail(clientEmail).getCompte().getIdCompte();
		try {
			Client clientFound = clientMetier.findByEmail(clientEmail);
			idClient = clientFound.getIdClient();
			clientMetier.updateListConnection(idClient, email);
			logger.info("Success adding : " + email);
			String info = "Connection adding !";
			model.addAttribute("info", info);

		} catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/transfer?idCompte=" + idCompte + "&error=" + e.getMessage();
		}

		return "redirect:/transfer";

	}

}
