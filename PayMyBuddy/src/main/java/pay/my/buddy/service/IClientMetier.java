package pay.my.buddy.service;

import pay.my.buddy.entities.Client;

/**
 * 
 * business interface that will allow implementation of application-specific
 * business processing
 * 
 * The important part is, the return value from operations which is a client
 * object
 * 
 * It is used to interact with the database, defining method related to the
 * client entity. Then is called/autowired in a controller layer.
 */
public interface IClientMetier {

	public Client addNewClient(Client client) throws Exception;

	public Client updateListConnection(Long idClient, String email) throws Exception;

	public Client findById(Long id) throws Exception;

	public Client findByEmail(String email) throws Exception;

}
