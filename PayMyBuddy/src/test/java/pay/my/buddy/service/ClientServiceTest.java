package pay.my.buddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import pay.my.buddy.dal.ClientRepository;
import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

	@Mock
	private ClientRepository clientRepository;

	@Mock
	private CompteRepository compteRepository;

	@InjectMocks
	private ClientMetierImpl clientMetier;
	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	public void TestFindById() throws Exception {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		client.setIdClient(1l);
		Optional<Client> optionalClient = Optional.of(client);
		when(clientRepository.findById(1l)).thenReturn(optionalClient);

		Client result = clientMetier.findById(1l);

		assertEquals(result.getIdClient(), 1l);
	}

	@Test
	public void TestFindById_whenIdDoesNotExist() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		client.setIdClient(1l);
		try {
			clientMetier.findById(2l);
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Client n'existe pas !");
		}

	}

	@Test
	void testGetClientByEmail() throws Exception {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Optional<Client> optionalClient = Optional.of(client);
		when(clientRepository.findClientByEmail("test@test.com")).thenReturn(optionalClient);

		Client result = clientMetier.findByEmail("test@test.com");

		assertEquals(result.getEmail(), "test@test.com");

	}

	@Test
	public void testGetClientByEmail_whenEmeilDoesNotExist() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		client.setIdClient(1l);
		try {
			clientMetier.findByEmail("email@test.com");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Client : email@test.com, n'existe pas !");
		}
	}

	@Test
	public void testAddNewClient() throws Exception {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Compte compte = new Compte();
		compte.setAmount(0);
		compte.setDateCreation(new Date());
		compte.setClient(client);
		when(clientRepository.save(client)).thenReturn(client);

		Client result = clientMetier.addNewClient(client);
		assertEquals(result.getEmail(), "test@test.com");
	}

	@Test
	public void testAddNewClient_whenClientAlreadyExists() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Compte compte = new Compte();
		compte.setAmount(0);
		compte.setDateCreation(new Date());
		compte.setClient(client);
		Optional<Client> optionalClient = Optional.of(client);
		when(clientRepository.findClientByEmail("test@test.com")).thenReturn(optionalClient);

		try {
			clientMetier.addNewClient(client);
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Client : test@test.com, existe deja!");
		}
	}

	@Test
	public void testUpdateListConnection() throws Exception {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Client connection = new Client("connectionFirstName", "connectionLastName", "connection@test.com",
				"connectiontest123");

		client.setIdClient(1l);
		Optional<Client> optionalClient = Optional.of(client);
		Optional<Client> optionalConnection = Optional.of(connection);
		when(clientRepository.findById(1l)).thenReturn(optionalClient);
		when(clientRepository.findClientByEmail("connection@test.com")).thenReturn(optionalConnection);

		clientMetier.updateListConnection(1l, "connection@test.com");
		
		assertNotNull(client.getConnections());
		assertTrue(client.getConnections().contains(connection));

	}

	@Test
	public void testUpdateListConnection_whenConnectionAlreadyExists() throws Exception {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Client connection = new Client("connectionFirstName", "connectionLastName", "connection@test.com",
				"connectiontest123");

		client.setIdClient(1l);
		Optional<Client> optionalClient = Optional.of(client);
		Optional<Client> optionalConnection = Optional.of(connection);
		client.getConnections().add(connection);  
		when(clientRepository.findById(1l)).thenReturn(optionalClient);
		when(clientRepository.findClientByEmail("connection@test.com")).thenReturn(optionalConnection);

		try {
		clientMetier.updateListConnection(1l, "connection@test.com");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Connection : connection@test.com, existe deja!");
		}

	}

	@Test
	public void testUpdateListConnection_whenEmailConnectionNotExists() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");

		client.setIdClient(1l);
		Optional<Client> optionalClient = Optional.of(client);

		when(clientRepository.findById(1l)).thenReturn(optionalClient);

		try {
			clientMetier.updateListConnection(1l, "connection@test.com");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Client : connection@test.com, n'existe pas !");
		}

	}

	@Test
	public void testUpdateListConnection_whenEmailConnectionBlank() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		client.setIdClient(1l);

		try {
			clientMetier.updateListConnection(1l, "");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("To add new connection, Email is mandatory !");
		}

	}

	@Test
	public void testUpdateListConnection_whenEmailConnectionEqualsEmailClient() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		client.setIdClient(1l);
		Optional<Client> optionalClient = Optional.of(client);

		when(clientRepository.findById(1l)).thenReturn(optionalClient);
		when(clientRepository.findClientByEmail("test@test.com")).thenReturn(optionalClient);

		try {
			clientMetier.updateListConnection(1l, "test@test.com");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Operation impossible : c'est votre email !");
		}

	}

}
