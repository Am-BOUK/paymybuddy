package pay.my.buddy.service;

import java.util.List;

import pay.my.buddy.entities.Compte;

public interface ICompteMetier {

	Compte consulterCompte(Long idCompte) throws Exception;

	Compte findCompteByIdClient(Long idClient) throws Exception;

	Compte addNewCompte(Compte compte) throws Exception;

	List<Compte> getAllComptes() throws Exception;

	void deleteCompteById(Long idCompte) throws Exception;

	void deleteCompteByIdClient(Long idClient) throws Exception;

}
