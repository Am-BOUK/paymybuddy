package pay.my.buddy.web;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pay.my.buddy.constants.DefaultValuePages;
import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;
import pay.my.buddy.entities.Operation;
import pay.my.buddy.service.ICompteMetier;
import pay.my.buddy.service.IOperationMetier;

/**
 * Implementing the management of interactions between the application user and
 * the application.
 *
 */
@Controller
public class CompteController {
	private static final Logger logger = LogManager.getLogger("CompteRestService");

	/**
	 * implementation of compte business processing
	 * 
	 */
	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private ICompteMetier compteMetier;
	
	@Autowired
	private IOperationMetier operationMetier;

	/**
	 * Read - Get all comptes
	 * 
	 * @return list of comptes
	 * @throws Exception 
	 */
	@RequestMapping(value = "/comptes", method = RequestMethod.GET)
	public String getAllComptes(Model model) throws Exception {
		logger.info("Getting All Comptes");
		List<Compte> comptes = compteMetier.getAllComptes();
		model.addAttribute("comptes", comptes);
		return "comptes/comptesList";
	}

	/**
	 * Read-Get one compte by id compte
	 * 
	 * @param id compte
	 * @return getCompte.html
	 * @throws Exception 
	 */
	@RequestMapping(value = "/compte", method = RequestMethod.GET)
	public String consulterCompte(Long idCompte, Model model,
			@RequestParam(name = "page" , defaultValue = DefaultValuePages.PAGE) int page,
			@RequestParam(name = "size", defaultValue = DefaultValuePages.SIZE) int size) {
		model.addAttribute("idCompte", idCompte);
		logger.info("Getting compte by code compte");
		try {
			Compte compte = compteMetier.consulterCompte(idCompte);
			Page<Operation> pageOperation = operationMetier.listOperation(idCompte, page, size);
			int[] pages = new int[pageOperation.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("listeOperation", pageOperation.getContent());
			model.addAttribute("compte", compte);
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}

		return "compteView";
	}
	
	/**
	 * Read-Get one compte by id compte
	 * 
	 * @param id compte
	 * @return getCompte.html
	 * @throws Exception 
	 */
	@RequestMapping(value = "/compteByIdClient", method = RequestMethod.GET)
	public String getClientByIdClient(Long idClient, Model model) throws Exception {
		model.addAttribute("idClient", idClient);
		logger.info("Getting compte by id client");
		try {
			Compte compte =compteMetier.findCompteByIdClient(idClient) ;
		model.addAttribute("compte", compte);
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		
		return "comptes/getCompteByIdClient";
	}

	
	/**
	 * Create - Add a new compte
	 * 
	 * @param compte object
	 * @return The compte object added
	 */
	@RequestMapping(value = { "/saveCompte" }, method = RequestMethod.GET)
	public String showAddComptePage() {
		return "comptes/addCompte";
	}

	
//	creer dans client
	@RequestMapping(value = { "/saveCompte" }, method = RequestMethod.POST)
	public String saveCompte() throws Exception {
		return "redirect:/saveClient";
	}
	
	/**
	 * Update - update an existing compte
	 * 
	 * @param email  the email of the client to update
	 * @param client the client object updated
	 * @return client the client object updated
	 */
	@RequestMapping(value = "/compte/{codeCompte}", method = RequestMethod.PUT)
	public Compte save(@PathVariable String codeCompte, @RequestBody Compte compte) {
//		compte.setCodeCompte(codeCompte);
		return compteRepository.save(compte);
	}
	
	/**
	 * Delete - delete a compte
	 * 
	 * @param codeCompte the codeCompte of the compte to delete
	 */
//	@RequestMapping(value = "/compte/{codeCompte}", method = RequestMethod.DELETE)
//	public boolean deleteByEmail(@PathVariable String codeCompte) {
//		compteRepository.deleteById(codeCompte);
//		return true;
//	}
	/**
	 * Handle specified types of exceptions ** Processing the validation errors:
	 * 
	 * @param ex argument of method not valid
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		logger.info("the specified Compte object is invalid : " + errors);
		return errors;
	}
}