package pay.my.buddy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pay.my.buddy.dal.ClientRepository;
import pay.my.buddy.entities.Client;

@Service
public class ClientMetierImpl implements IClientMetier {
	private static final Logger logger = LogManager.getLogger("ClientMetierImpl");

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public Client findById(Long id) throws Exception {
		logger.info("find client by id : " + id);

		try {
			Client clientfound = clientRepository.findById(id).get();
			logger.info("client found : " + clientfound.getFirstName() + " " + clientfound.getLastName());
			return clientfound;
		} catch (Exception e) {
			logger.info("id not found!");
			throw new Exception("id not found!");
		}
	}

//	TODO : ne fonctionne pas!!!

	@Override
	public Client getById(Long id) throws Exception {
		logger.info("getting client by id : " + id);
		try {
			Client getClient = clientRepository.getById(id);
			logger.info("get client by id : " + getClient);
			return getClient;
		} catch (Exception e) {
			logger.info("can not getting client, id not found!");
			throw new Exception("can not getting client, id not found!");
		}
	}

	public boolean existsById(Long id) {
		return clientRepository.existsById(id);
	}

	@Override
	public List<Client> findClientByFirstNameOrLastName(String name) throws Exception {
		logger.info("find client by name : " + name);
		List<Client> clients = getAllClients();
		List<Client> clientsFound = new ArrayList<>();
		for (Client client : clients) {
			if (client.getFirstName().toLowerCase().contains(name.toLowerCase())
					|| client.getLastName().toLowerCase().contains(name.toLowerCase())) {
				clientsFound.add(client);
			}

		}
		if (clientsFound.isEmpty()) {
			logger.info("The list of client " + name + ", you want to get, is empty !");
			throw new Exception("The list of client " + name + ", you want to get, is empty !");
		}
		System.out.println(clientsFound.get(0).getEmail());
		return clientsFound;

	}

//	public Page<Client> findClientByName(String name, int page, int size) throws Exception {
//		logger.info("find client by name : " + name);
//		Page<Client> clientsFound = clientRepository.findClientByName( name , PageRequest.of(page, size));
//		System.out.println(clientsFound.get().count());
//		return clientsFound;
//
//	}

	@Override
	public Client findClientByEmail(String email) throws Exception {
		logger.info("find client by email : " + email);
		try {
			Client clientfound = clientRepository.findClientByEmail(email);
			logger.info("client found : " + clientfound.getFirstName() + " " + clientfound.getLastName());
			return clientfound;
		} catch (Exception e) {
			logger.info("email not found!");
			throw new Exception("email not found!");
		}

	}

	@Override
	public Client addNewClient(Client client) throws Exception {
		logger.info("adding client " + client.getLastName() + " " + client.getFirstName());
		try {
			Client clientAdded = clientRepository.save(client);
			logger.info("client added !");
			return clientAdded;
		} catch (Exception e) {
			logger.info("can not addind client, email is already exist " + client.getEmail());
			throw new Exception("email is already exist !");

		}
	}

	@Override
	public List<Client> getAllClients() throws Exception {
		try {
			List<Client> getAllClients = clientRepository.findAll();
			logger.info("getting liste of all clients ");
			return getAllClients;
		} catch (Exception e) {
			logger.info("can not getting list of all clients");
			throw new Exception("can not getting list of all clients !");
		}

	}

	@Override
	public Page<Client> getAllClientsByPage(int page, int size) throws Exception {
		try {
			Page<Client> getAllClients = clientRepository.findAll(PageRequest.of(page, size));
			logger.info("getting liste of all clients ");
			return getAllClients;
		} catch (Exception e) {
			logger.info("can not getting list of all clients");
			throw new Exception("can not getting list of all clients !");
		}
	}

	@Override
	public void deleteClientById(Long id) throws Exception {
		logger.info("delete client with id : " + id);

		try {
			clientRepository.deleteById(id);
			logger.info("delete client with success !");

		} catch (Exception e) {
			logger.info("can not delete client");
			throw new Exception("can not delete client, id_client does not exist !");
		}

	}

	@Override
	public void deleteClientByEmail(String email) throws Exception {
		logger.info("delete client with email : " + email);
		try {
			Client clientFound = findClientByEmail(email);
			clientRepository.delete(clientFound);
			logger.info("delete client with success !");

		} catch (Exception e) {
			logger.info("can not delete client");
			throw new Exception("can not delete client, email_client does not exist !");
		}

	}

}
