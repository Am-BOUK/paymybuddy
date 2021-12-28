package pay.my.buddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import pay.my.buddy.entities.Client;

public interface IClientMetier {

	Client findById(Long id) throws Exception;

	Client getById(Long id) throws Exception;

	List<Client> findClientByFirstNameOrLastName(String name) throws Exception;

	Client findClientByEmail(String email) throws Exception;

	Client addNewClient(Client client) throws Exception;

	List<Client> getAllClients() throws Exception;

	Page<Client> getAllClientsByPage(int page, int size) throws Exception;

	void deleteClientById(Long id) throws Exception;

	void deleteClientByEmail(String email) throws Exception;

}
