package pay.my.buddy.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pay.my.buddy.constants.DefaultValuePages;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;
import pay.my.buddy.entities.Operation;
import pay.my.buddy.service.ICompteMetier;
import pay.my.buddy.service.IOperationMetier;

/**
 * Implementing the management of interactions between the application compte
 * and the application.
 *
 */
@Controller
public class CompteController {
	private static final Logger logger = LogManager.getLogger("CompteController");

	/**
	 * Calling operations of the compte service
	 */
	@Autowired
	private ICompteMetier compteMetier;

	/**
	 * Calling operations of the operation service
	 */
	@Autowired
	private IOperationMetier operationMetier;

	/**
	 * Consult / get compte by id
	 * 
	 * @param idCompte : id of the compte we want to get his information
	 * @param model    : Model defines a holder for compte attributes and is
	 *                 primarily designed for adding attributes to the model
	 * @param page     : page of operations by compte
	 * @param size     : size of operations by page
	 * @return a compte page web
	 * @throws Exception
	 */
	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	public String consulterCompte(Long idCompte, Model model,
			@RequestParam(name = "page", defaultValue = DefaultValuePages.PAGE) int page,
			@RequestParam(name = "size", defaultValue = DefaultValuePages.SIZE) int size) {

		logger.info("Getting compte by code compte");
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String clientEmail = authentication.getName();
			Compte clientFound = compteMetier.findCompteByClientEmail(clientEmail);
			idCompte = clientFound.getIdCompte();
			model.addAttribute("idCompte", idCompte);
			Compte compte = compteMetier.consulterCompte(idCompte);
			List<Client> connectionsList = compte.getClient().getConnections();
			model.addAttribute("connectionsList", connectionsList);
			Page<Operation> pageOperation = operationMetier.listOperation(idCompte, page, size);
			int[] pages = new int[pageOperation.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("listeOperation", pageOperation.getContent());
			model.addAttribute("compte", compte);
			logger.info("Success getting compte");

		} catch (Exception e) {
			logger.info("Failure getting compte");
			model.addAttribute("exception", e);
		}

		return "compteView";
	}

	/**
	 * show Add Compte web page ** this method allows to show the web page that will
	 * direct us to the registration form
	 * 
	 * @return a saveCompte page web
	 */
	@RequestMapping(value = { "/saveCompte" }, method = RequestMethod.GET)
	public String showAddComptePage() {
		logger.info("show Add Compte web page");
		return "comptes/addCompte";
	}

	/**
	 * save compte ** this method allows to direct us to the registeration form
	 * 
	 * 
	 * @return a saveClient web page
	 */
	@RequestMapping(value = { "/saveCompte" }, method = RequestMethod.POST)
	public String saveCompte() throws Exception {
		logger.info("redirect to save client web page");

		return "redirect:/saveClient";
	}

}
