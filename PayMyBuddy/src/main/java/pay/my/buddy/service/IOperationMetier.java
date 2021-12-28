package pay.my.buddy.service;

import org.springframework.data.domain.Page;

import pay.my.buddy.entities.Compte;
import pay.my.buddy.entities.Operation;

public interface IOperationMetier {

	public void verser(Long codeCompte, double montant, String descirtion) throws Exception;

	public void retirer(Long codeCompte, double montant, String description) throws Exception;

	public void virement(Long codeCompte1, Long codeCompte2, double montant, String description) throws Exception;

	public Page<Operation> listOperation(Long codeCompte, int page, int size);
}
