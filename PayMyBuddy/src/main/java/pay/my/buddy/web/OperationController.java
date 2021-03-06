package pay.my.buddy.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pay.my.buddy.entities.Compte;
import pay.my.buddy.service.ICompteMetier;
import pay.my.buddy.service.IOperationMetier;

/**
 * Implementing the management of interactions between the application operation
 * and the application.
 *
 */
@Controller
public class OperationController {
	private static final Logger logger = LogManager.getLogger("OperationController");
	/**
	 * Calling methods of the operation service
	 * 
	 */
	@Autowired
	private IOperationMetier operationMetier;
	/**
	 * Calling methods of the compte service
	 * 
	 */
	@Autowired
	private ICompteMetier compteMetier;

	/**
	 * save operation ** this method allows to save a operation to database
	 * 
	 * @param model         : Model defines a holder for client attributes and is
	 *                      primarily designed for adding attributes to the model
	 * @param typeOperation : one of giving operation type (transfer, versement,
	 *                      retrait)
	 * 
	 * @param idCcompte     : id of the compte making operation
	 * @param amount        : amount of the operation
	 * @param description   : the description of the operation
	 * @param email         : email of the connection if we want make a transfer
	 *                      operation
	 * 
	 * @return a compte page web
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOperation", method = RequestMethod.POST)
	public String saveOperation(Model model, String typeOperation, Long idCompte, double amount, String description,
			String email) {
		logger.info("save operation");

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String clientEmail = authentication.getName();
			Compte clientFound = compteMetier.findCompteByClientEmail(clientEmail);
			idCompte = clientFound.getIdCompte();

			if (typeOperation.equals("VERS")) {
				logger.info("save payment operation");
				operationMetier.verser(idCompte, amount, description);
			} else if (typeOperation.equals("RETR")) {
				logger.info("save withdraw operation");
				operationMetier.retirer(idCompte, amount, description);
			} else if (typeOperation.equals("VIR")) {
				logger.info("save transfer operation");
				operationMetier.virement(idCompte, email, amount, description);
			}
		} catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/transfer?idCompte=" + idCompte + "&error=" + e.getMessage();
		}

		return "redirect:/transfer?idCompte=" + idCompte;

	}

}
