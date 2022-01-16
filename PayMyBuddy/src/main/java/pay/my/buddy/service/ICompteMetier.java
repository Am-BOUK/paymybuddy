package pay.my.buddy.service;

import pay.my.buddy.entities.Compte;

/**
 * 
 * business interface that will allow implementation of application-specific
 * business processing
 * 
 * The important part is, the return value from operations which is a compte
 * object
 * 
 * It is used to interact with the database, defining method related to the
 * compte entity. Then is called/autowired in a controller layer.
 */
public interface ICompteMetier {

	public Compte consulterCompte(Long idCompte) throws Exception;

	public Compte findCompteByIdClient(Long idClient) throws Exception;

	public Compte addNewCompte(Compte compte) throws Exception;

	public Compte findCompteByClientEmail(String email) throws Exception;

	public Compte updateCompte(Compte compte);

}
