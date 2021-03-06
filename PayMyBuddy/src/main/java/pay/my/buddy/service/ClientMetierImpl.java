package pay.my.buddy.service;

import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pay.my.buddy.dal.ClientRepository;
import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;

/**
 * implementation of client business processing
 * 
 */
@Service
public class ClientMetierImpl implements IClientMetier {
	private static final Logger logger = LogManager.getLogger("ClientMetierImpl");

	/**
	 * Calling operations of the client repository
	 */
	@Autowired
	private ClientRepository clientRepository;

	/**
	 * Calling operations of the compte repository
	 */
	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Find a client by his id ** This operation allows to check if the id of the
	 * client we want to find already exist in the data base, then returns a client
	 * object
	 * 
	 * @param id of the client to find
	 * @return client object
	 * @throws Exception
	 */
	@Override
	public Client findById(Long id) throws Exception {
		logger.info("find client by id : " + id);

		Optional<Client> clientfound = clientRepository.findById(id);
		if (clientfound.isEmpty()) {
			throw new Exception("Client n'existe pas !");
		}
		logger.info("client found : " + clientfound.get().getFirstName() + " " + clientfound.get().getLastName());
		return clientfound.get();

	}

	/**
	 * Find a client by his email ** This operation allows to check if the address
	 * mail of the client we want to find already exist in the data base, then
	 * returns a client object
	 * 
	 * @param email of the client to find
	 * @return client object
	 * @throws Exception
	 */
	@Override
	public Client findByEmail(String email) throws Exception {
		logger.info("find client by id : " + email);
		Optional<Client> clientfound = clientRepository.findClientByEmail(email);
		if (clientfound.isEmpty()) {
			throw new Exception("Client : " + email + ", n'existe pas !");
		}

		logger.info("client found : " + clientfound.get().getFirstName() + " " + clientfound.get().getLastName());
		return clientfound.get();

	}

	/**
	 * Add a new client ** This operation allows to check if the email of the client
	 * we want to add already exist in the data base, then allows to add it
	 * 
	 * @param client object to add
	 * @return client object added
	 * @throws Exception
	 */
	@Override
	public Client addNewClient(Client client) throws Exception {
		logger.info("adding client " + client.getEmail());

		Optional<Client> clientfound = clientRepository.findClientByEmail(client.getEmail());
		if (clientfound.isEmpty()) {
			client.setPassword(passwordEncoder.encode(client.getPassword()));

			Client clientAdded = clientRepository.save(client);

			Compte compte = new Compte();
			compte.setAmount(0);
			compte.setDateCreation(new Date());
			compte.setClient(client);
			compteRepository.save(compte);
			logger.info("client and his compte added !");
			return clientAdded;
		} else {
			logger.info("Client : " + client.getEmail() + ", existe deja!");

			throw new Exception("Client : " + client.getEmail() + ", existe deja!");
		}

	}

	/**
	 * Update connections list of the client ** This operation allows to check if
	 * the id of the client we want to update his connections list already exist in
	 * the data base and to check if the email of connection we want to add to the
	 * client is already exist in the data base, then allows to update the update
	 * the connections list of the client by adding a new connection
	 * 
	 * @param idClient the id of the client we want to add his connection
	 * @param email    the email of the connection we want to add to the client
	 * 
	 * @return client object updated
	 * @throws Exception
	 */
	@Override
	public Client updateListConnection(Long idClient, String email) throws Exception {
		if (email.isEmpty()) {
			throw new Exception("To add new connection, Email is mandatory !");
		}

		logger.info("adding connection : " + email);

		Client clientFound = findById(idClient);
		Client connection = findByEmail(email);

		if (clientFound.getEmail().contains(email)) {
			throw new Exception("Operation impossible : c'est votre email !");
		}

		logger.info("adding connection !");
		if (clientFound.getConnections().contains(connection)) {
			logger.info("Connection : " + email + ", existe deja!");
			throw new Exception("Connection : " + email + ", existe deja!");
		}
		clientFound.getConnections().add(connection);
		return clientRepository.saveAndFlush(clientFound);

	}

}
