package pay.my.buddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
//import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.dal.OperationRepository;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;
import pay.my.buddy.entities.Operation;
import pay.my.buddy.entities.Retrait;
import pay.my.buddy.entities.Versement;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {
	@Mock
	private OperationRepository operationRepository;

	@Mock
	private CompteRepository compteRepository;

	@InjectMocks
	private OperationMetrierImpl operationMetier;

	@Test
	void testVerser() throws Exception {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");

		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(1l);

		when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(new Versement());
		when(compteRepository.save(Mockito.any(Compte.class))).thenReturn(compte);
		when(compteRepository.findById(Mockito.any())).thenReturn(Optional.of(compte));

		Compte compteAfterVersement = operationMetier.verser(compte.getIdCompte(), 200, "versement");

		assertEquals(compteAfterVersement.getAmount(), 200 - 200 * 0.5 / 100);

	}

	@Test
	void testVerser_WhenAmountIsNull_shouldReturnException() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(1l);
		try {
			operationMetier.verser(compte.getIdCompte(), 0, "versement");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Amount and description are mandatory, they can't be null !");
		}

	}

	@Test
	void testVerser_WhenDescriptionIsNull_shouldReturnException() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Compte compte = new Compte(new Date(), 0, client);
		compte.setIdCompte(1l);
		try {
			operationMetier.verser(compte.getIdCompte(), 200, "");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Amount and description are mandatory, they can't be null !");
		}

	}

	@Test
	void testRetirer() throws Exception {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");

		Compte compte = new Compte(new Date(), 200, client);
		compte.setIdCompte(1l);

		when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(new Retrait());
		when(compteRepository.save(Mockito.any(Compte.class))).thenReturn(compte);
		when(compteRepository.findById(Mockito.any())).thenReturn(Optional.of(compte));

		Compte compteAfterVersement = operationMetier.retirer(compte.getIdCompte(), 199, "versement");

		assertEquals(compteAfterVersement.getAmount(), 0.005);

	}

	@Test
	void testRetirer_WhenAmountIsNull_shouldReturnException() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Compte compte = new Compte(new Date(), 200, client);
		compte.setIdCompte(1l);
		try {
			operationMetier.retirer(compte.getIdCompte(), 0, "versement");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Amount and description are mandatory, they can't be null !");
		}

	}

	@Test
	void testRetirer_WhenDescriptionIsNull_shouldReturnException() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");
		Compte compte = new Compte(new Date(), 200, client);
		compte.setIdCompte(1l);
		try {
			operationMetier.retirer(compte.getIdCompte(), 10, "");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Amount and description are mandatory, they can't be null !");
		}
	}

	/**
	 * test retirer methode when amount to be withdrawn from the account is larger
	 * than amount in account should return exception
	 * 
	 */
	@Test
	void testRetirer_WhenAmount_shouldReturnException() {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");

		Compte compte = new Compte(new Date(), 200, client);
		compte.setIdCompte(1l);

		when(compteRepository.findById(Mockito.any())).thenReturn(Optional.of(compte));

		try {
			operationMetier.retirer(compte.getIdCompte(), 400, "versement");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Solde insuffisant!");
		}
	}

	@Test
	void testVirement() throws Exception {
		Client clientSender = new Client("senderFirstName", "senderLastName", "sender@test.com", "sendertest123");
		Compte compteSender = new Compte(new Date(), 200, clientSender);
		compteSender.setIdCompte(1l);

		when(compteRepository.findById(Mockito.any())).thenReturn(Optional.of(compteSender));

		Client clientReceiper = new Client("receiperFirstName", "receiperLastName", "receiper@test.com",
				"receipertest123");
		Compte compteReceiper = new Compte(new Date(), 0, clientReceiper);

		when(compteRepository.findCompteByClientEmail(Mockito.any())).thenReturn(Optional.of(compteReceiper));

		Compte compteSenderAfterVirement = operationMetier.virement(compteSender.getIdCompte(),
				clientReceiper.getEmail(), 100, "Virement");

		assertEquals(compteSenderAfterVirement.getAmount(), 200 - (100 + 100 * 0.5 / 100));
	}

	@Test
	public void testVirement_whenSenderIsReceiper() {
		Client clientSender = new Client("senderFirstName", "senderLastName", "sender@test.com", "sendertest123");
		Compte compteSender = new Compte(new Date(), 200, clientSender);
		compteSender.setIdCompte(1l);
		when(compteRepository.findById(Mockito.any())).thenReturn(Optional.of(compteSender));
		Client clientReceiper = new Client("senderFirstName", "senderLastName", "sender@test.com", "sendertest123");
		when(compteRepository.findCompteByClientEmail(Mockito.any())).thenReturn(Optional.of(compteSender));
		try {
			operationMetier.virement(compteSender.getIdCompte(), clientReceiper.getEmail(), 100, "Virement");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Operation impossible : virement sur le meme compte");
		}
	}

	@Test
	public void testVirement_WhenSoldeInsuffisant() {
		Client clientSender = new Client("senderFirstName", "senderLastName", "sender@test.com", "sendertest123");
		Compte compteSender = new Compte(new Date(), 200, clientSender);
		compteSender.setIdCompte(1l);

		when(compteRepository.findById(Mockito.any())).thenReturn(Optional.of(compteSender));

		Client clientReceiper = new Client("receiperFirstName", "receiperLastName", "receiper@test.com",
				"receipertest123");
		Compte compteReceiper = new Compte(new Date(), 0, clientReceiper);

		when(compteRepository.findCompteByClientEmail(Mockito.any())).thenReturn(Optional.of(compteReceiper));

		try {
			operationMetier.virement(compteSender.getIdCompte(), clientReceiper.getEmail(), 400, "Virement");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertThat(e.getMessage()).contains("Solde insuffisant!");
		}

	}

	@Test
	public void testListOperation() throws Exception {
		Client client = new Client("firstName", "LastName", "test@test.com", "testtest123");

		Compte compte = new Compte(new Date(), 200, client);
		compte.setIdCompte(1l);
		compte.setOperations(new ArrayList<Operation>());
		
		Operation versement = new Versement(new Date(), 200, "versement", compte);
		Operation retrait = new Retrait(new Date(), 200, "retrait", compte);
		
		compte.getOperations().add(retrait);
		compte.getOperations().add(versement);
		
		List<Operation> listOperation = new ArrayList<Operation>();
		listOperation.add(retrait);
		listOperation.add(versement);
		
		Page<Operation> pageOperation =new PageImpl<Operation>(listOperation);
	
		when(operationRepository.listOperation(1l, PageRequest.of(0, 2))).thenReturn(pageOperation);
		
		System.out.println(operationRepository.listOperation(1l, PageRequest.of(0, 2)).getSize());
		
		Page<Operation> result = operationMetier.listOperation(1l, 0, 2);
		
		assertEquals(result.getContent().size(),2);


	}

}
