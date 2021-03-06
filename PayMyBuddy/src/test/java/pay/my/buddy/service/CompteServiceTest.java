package pay.my.buddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;

@ExtendWith(MockitoExtension.class)
class CompteServiceTest {
	@Mock
	private CompteRepository compteRepository;

	@InjectMocks
	private CompteMetierImpl compteMetier;

	@Test
	void testConsulterCompte() throws Exception {
		Client client = new Client();
		client.setEmail("test@test.com");
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(1l);
		when(compteRepository.findById(1l)).thenReturn(Optional.of(compte));
		Compte result = compteMetier.consulterCompte(1l);
		assertEquals(result.getClient().getEmail(), "test@test.com");

	}

	@Test
	void testConsulterCompte_whenCompteNotExists() {
		Client client = new Client();
		client.setEmail("test@test.com");
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(1l);

		try {
			compteMetier.consulterCompte(1l);
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Compte introuvable");
		}
	}

	@Test
	void testFindCompteByIdClient() throws Exception {
		Client client = new Client();
		client.setIdClient(1l);
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(2l);
		when(compteRepository.findCompteByIdClient(1l)).thenReturn(compte);
		Compte result = compteMetier.findCompteByIdClient(1l);
		assertEquals(result.getIdCompte(), 2l);

	}

	@Test
	void testFindCompteByIdClient_() {
		Client client = new Client();
		client.setIdClient(1l);
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(2l);
		try {
			compteMetier.findCompteByIdClient(1l);
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("compte not found!");
		}

	}

	@Test
	void testAddNewCompte() throws Exception {
		Client client = new Client();
		client.setIdClient(1l);
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(2l);
		when(compteRepository.save(compte)).thenReturn(compte);
		Compte result = compteMetier.addNewCompte(compte);
		assertEquals(result.getIdCompte(), 2l);

	}

	@Test
	void testAddNewCompte_WhenCompteExists() {
		Client client = new Client();
		client.setEmail("test@test.com");
		client.setIdClient(1l);
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(2l);
		when(compteRepository.findCompteByIdClient(1l)).thenReturn(compte);
		try {
			compteMetier.addNewCompte(compte);
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("compte is already exist !");
		}
	}
	

	@Test
	void testUpdateCompte() throws Exception {
		Client client = new Client();
		client.setIdClient(1l);
		Compte compte = new Compte(new Date(), 0, client);
		compte.setAmount(200);
		compte.setIdCompte(2l);
		when(compteRepository.saveAndFlush(compte)).thenReturn(compte);
		Compte result = compteMetier.updateCompte(compte);
		assertEquals(result.getAmount(), 200);

	}
	
	@Test
	void testFindCompteByClientEmail() throws Exception {
		Client client = new Client();
		client.setEmail("test@test.com");
		client.setIdClient(1l);
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(2l);
		when(compteRepository.findCompteByClientEmail("test@test.com")).thenReturn(Optional.of(compte));
		Compte result = compteMetier.findCompteByClientEmail("test@test.com");
		assertEquals(result.getIdCompte(), 2l);

	}
	
	@Test
	void testFindCompteByClientEmail_whenCompteDoesNotExist() {
		Client client = new Client();
		client.setEmail("test@test.com");
		client.setIdClient(1l);
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(2l);

		try {
			compteMetier.findCompteByClientEmail("test@test.com");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Compte n'existe pas !");
		}

	}
	
}
