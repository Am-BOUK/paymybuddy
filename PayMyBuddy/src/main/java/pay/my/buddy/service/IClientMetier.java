package pay.my.buddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import pay.my.buddy.entities.Client;

public interface IClientMetier {
	Client addNewClient(Client client) throws Exception;

	List<Client> getAllClients() throws Exception;

	Page<Client> getAllClientsByPage(int page, int size);

	Client updateListConnection(Long idClient, String email) throws Exception;

	Client findById(Long id) throws Exception;/**/

}
