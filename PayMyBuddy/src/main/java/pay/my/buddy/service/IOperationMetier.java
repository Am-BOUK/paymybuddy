package pay.my.buddy.service;

import org.springframework.data.domain.Page;

import pay.my.buddy.entities.Compte;
import pay.my.buddy.entities.Operation;

/**
 * 
 * business interface that will allow implementation of application-specific
 * business processing
 * 
 * The important part is, the return value from operations which is a operation
 * object
 * 
 * It is used to interact with the database, defining method related to the
 * operation entity. Then is called/autowired in a controller layer.
 */
public interface IOperationMetier {

	public Compte verser(Long codeCompte, double montant, String descirtion) throws Exception;

	public Compte retirer(Long codeCompte, double montant, String description) throws Exception;

	public Page<Operation> listOperation(Long codeCompte, int page, int size);

	public Compte virement(Long idSender, String emailReceiper, double montant, String description) throws Exception;

}
