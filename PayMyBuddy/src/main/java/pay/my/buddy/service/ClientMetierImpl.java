package pay.my.buddy.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pay.my.buddy.dal.ClientRepository;
import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;

@Service
public class ClientMetierImpl implements IClientMetier {
	private static final Logger logger = LogManager.getLogger("ClientMetierImpl");

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private CompteRepository compteRepository;

	@Override /**/
	public Client findById(Long id) throws Exception {
		logger.info("find client by id : " + id);

		Optional<Client> clientfound = clientRepository.findById(id);
		if (clientfound.isEmpty()) {
			throw new Exception("Client non trouvé !");
		}
		logger.info("client found : " + clientfound.get().getFirstName() + " " + clientfound.get().getLastName());
		return clientfound.get();

	}

	private Client findByEmail(String email) throws Exception {
		logger.info("find client by id : " + email);
		Optional<Client> clientfound = clientRepository.findClientByEmail(email);
		if (clientfound.isEmpty()) {
			throw new Exception("Client non trouvé !");
		}

		logger.info("client found : " + clientfound.get().getFirstName() + " " + clientfound.get().getLastName());
		return clientfound.get();

	}

	@Override
	public Client addNewClient(Client client) throws Exception {
		logger.info("adding client " + client.getEmail());

		Optional<Client> clientfound = clientRepository.findClientByEmail(client.getEmail());
		if (clientfound.isEmpty()) {
			Client clientAdded = clientRepository.save(client);
			Compte compte = new Compte();
			compte.setAmount(0);
			compte.setDateCreation(new Date());
			compte.setClient(client);
			compteRepository.save(compte);
			logger.info("client and his compte added !");
			return clientAdded;
		} else {
			logger.info("Client : "+client.getEmail()+", existe déjà!");

			throw new Exception("Client existe déjà!");
		}

	}

	@Override
	public List<Client> getAllClients() {
		List<Client> getAllClients = clientRepository.findAll();
		logger.info("getting liste of all clients ");
		return getAllClients;

	}

	@Override
	public Page<Client> getAllClientsByPage(int page, int size) {
		Page<Client> getAllClients = clientRepository.findAll(PageRequest.of(page, size));
		logger.info("getting liste of all clients ");
		return getAllClients;

	}

	@Override
	public Client updateListConnection(Long idClient, String email) throws Exception {
		logger.info("adding connection : " + email);

		Client clientFound = findById(idClient);
		Client connection = findByEmail(email);

		logger.info("adding connection !");

		clientFound.getConnections().add(connection);

		return clientRepository.saveAndFlush(clientFound);

	}

}
