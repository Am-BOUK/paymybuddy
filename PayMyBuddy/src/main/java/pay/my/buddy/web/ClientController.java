package pay.my.buddy.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pay.my.buddy.constants.DefaultValuePages;
import pay.my.buddy.dal.ClientRepository;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;
import pay.my.buddy.service.IClientMetier;
import pay.my.buddy.service.ICompteMetier;

/**
 * Implementing the management of interactions between the application user and
 * the application.
 *
 */
@Controller
public class ClientController {
	private static final Logger logger = LogManager.getLogger("ClientController");
	/**
	 * implementation of client business processing
	 * 
	 */

	@Autowired
	private IClientMetier clientMetier;

	/**
	 * Read - Get all clients
	 * 
	 * @return list of clients
	 * @throws Exception
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public String getAllClients(Model model) throws Exception {
		logger.info("Getting All Clients");
		List<Client> clients = clientMetier.getAllClients();
		model.addAttribute("clients", clients);
		return "clients/clientsList";
	}

	/**
	 * Read-Get one client by email
	 * 
	 * @param email
	 * @return a client object full filled
	 */
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String getClientById(Long idClient, Model model,
			@RequestParam(name = "page", defaultValue = DefaultValuePages.PAGE) int page,
			@RequestParam(name = "size", defaultValue = DefaultValuePages.SIZE) int size) {
		logger.info("Getting client by id");
		model.addAttribute("idClient", idClient);
		try {
			Client client = clientMetier.findById(idClient);
			List<Client> connectionsList = client.getConnections();
			model.addAttribute("client", client);
			model.addAttribute("connectionsList", connectionsList);

		} catch (Exception e) {
			model.addAttribute("exception", e);
		}

		return "clients/getClient";
	}

	@RequestMapping(value = { "/saveClient" }, method = RequestMethod.GET)
	public String showAddPersonPage(Model model) {
		Client client = new Client();
		model.addAttribute("client", client);
		return "clients/addClient";
	}

	@RequestMapping(value = { "/saveClient" }, method = RequestMethod.POST)
	public String savePerson(Model model, @Valid @ModelAttribute("client") Client client, BindingResult result)
			throws Exception {
		if (result.hasErrors()) {
			return "clients/addClient";
		}
		try {
			clientMetier.addNewClient(client);
			return "redirect:/clients";
		} catch (Exception e) {
			model.addAttribute("exception", e);
			return "clients/addClient";
		}

	}

	@RequestMapping(value = "/saveConnection", method = RequestMethod.POST)
	public String saveOperation(Model model, String email, Long idClient) {
		try {

			clientMetier.updateListConnection(idClient, email);

		} catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/client?idClient=" + idClient + "&error=" + e.getMessage();
		}

		return "redirect:/client?idClient=" + idClient;

	}

}
