package pay.my.buddy;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pay.my.buddy.dal.ClientRepository;
import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.dal.OperationRepository;
import pay.my.buddy.entities.Client;
import pay.my.buddy.entities.Compte;
import pay.my.buddy.entities.Retrait;
import pay.my.buddy.entities.Versement;
import pay.my.buddy.entities.VirementReceive;
import pay.my.buddy.entities.VirementSent;
import pay.my.buddy.service.ClientMetierImpl;
import pay.my.buddy.service.CompteMetierImpl;
import pay.my.buddy.service.IClientMetier;
import pay.my.buddy.service.IOperationMetier;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private IClientMetier clientMetier;

	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private CompteMetierImpl compteMetier;

	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private IOperationMetier operationMetier;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
//		Client client1 = clientRepository.save(new Client("Amal", "BOUKILI", "amal@gmail.com", "amal123"));
//		Client client2 = clientRepository.save(new Client("Rostow", "GOKING", "rostow@gmail.com", "rostow123"));
//
//		Compte compte1 = compteRepository.save(new Compte(new Date(), 3000, client1));
//		Compte compte2 = compteRepository.save(new Compte(new Date(), 5000, client2));
//
//		operationRepository.save(new Versement(new Date(), 300, "versement sur mon compte", compte1));
//		operationRepository.save(new Versement(new Date(), 200, "versement sur mon compte", compte1));
//		operationRepository.save(new Versement(new Date(), 200, "versement sur mon compte", compte1));
//		operationRepository.save(new Retrait(new Date(), 1000, "versement sur mon compte", compte1));
//
//		operationRepository.save(new Versement(new Date(), 100, "versement sur mon compte", compte2));
//		operationRepository.save(new Versement(new Date(), 1000, "versement sur mon compte", compte2));
//		operationRepository.save(new Versement(new Date(), 300, "versement sur mon compte", compte2));
//		operationRepository.save(new Retrait(new Date(), 500, "versement sur mon compte", compte2));
//
//		operationRepository.save(new VirementReceive(new Date(), 200, "cadeau anniversaire", compte2, (long) 3));
//		operationRepository.save(new VirementSent(new Date(), 200, "cadeau anniversaire", compte1, (long) 4));

//		payMyBuddyMetier.verser((long) 3, 150, "versement");
//		payMyBuddyMetier.retirer((long) 4, 500, "retrait de mon compte");
//		payMyBuddyMetier.virement((long) 4, (long) 3, 500, "frais d'études");

//		clientMetier.findClientByEmail("amal@gmail.com");
		clientMetier.findById((long) 2);
//		clientMetier.findClientByName("Amal", 3, 2);
//		clientMetier.findClientByFirstNameOrLastName("al");
//		clientMetier.findClientByFirstNameOrLastName("toto");

//		clientMetier.addNewClient(new Client("Jacob", "Boyd", "jacob@gmail.com", "jacob123"));
//		clientMetier.addNewClient(new Client("Jacob", "Boyd", "jacob@gmail.com", "jacob123"));
//		clientMetier.addNewClient(new Client("Tarik", "Fakhri", "jacob@gmail.com", "jacob123"));

		clientMetier.getAllClients();
//		clientMetier.deleteClientByEmail("jacob@gmail.com");

//		clientMetier.getById((long) 2);

//		clientMetier.deleteClientById((long) 10);
//		clientMetier.findById((long) 100);

//		clientMetier.findClientByEmail("toto@gmail.com");

//		Client client9 = clientRepository.save(new Client("NADAH", "nadah@gmail.com","admin123"));
//		Compte compte10 = compteRepository.save(new Compte("c10", new Date(), 10000, client9));
//		operationRepository.save(new Virement(new Date(), 300, "prête",compte10 , compte2));
//		payMyBuddyMetier.virement("c1", "c8", 700, "Trip prête");
//		payMyBuddyMetier.virement("c5", "c8", 1000, "Frais Soutenance");

		compteMetier.findCompteByIdClient((long) 2);
//		compteMetier.findCompteByIdClient((long) 200);

//		compteMetier.findById((long) 3);
		compteMetier.consulterCompte((long) 4);
//		Client client= clientMetier.addNewClient(new Client("Jacob", "Boyd", "jacob@gmail.com", "jacob123"));
//		compteMetier.addNewCompte(new Compte(new Date(), 6000, client));
//		compteMetier.addNewCompte(new Compte(new Date(), 2500, client));
		compteMetier.getAllComptes();

//		operationMetier.retirer((long) 3, 250, "retrait");
//		operationMetier.verser((long) 37, 3500, "alimentation du compte");

//		List<Client> clientList= clientRepository.findByFirstNameOrLastNameContains("m","maach");
//		
//		clientList.forEach(c->{
//			System.out.println(c.getEmail());
//		});
//		System.out.println(clientRepository.findByFirstNameAndLastNameContains("amal","boukili").getEmail());

//		Client client1 = new Client("amina", "sadiqi", "amina@gmail.com", "sadiqi1234");
//		clientMetier.addNewClient(client1);
//
//		Client client2 = new Client("Mohammed", "Daoudi", "mohammed@gmail.com", "123456");
//		clientMetier.addNewClient(client2);
//
//		Client client3 = new Client("Sarah", "Bentabet", "bentabet@gmail.com", "789654");
//		clientMetier.addNewClient(client3);
//
//		client3.setConnections(Arrays.asList(client2, client1));
//
//		clientRepository.save(client3);
//
//		client3.getConnections();
		
//		Client client3 = clientMetier.findById(126l);
//		System.out.println(client3.getConnections());
		
//		System.out.println(client3.getConnections().get(1).getEmail());

//		clientMetier.getListConnection(126l);
//		System.out.println(clientMetier.getListConnection(126l));

//		connectionsList.forEach(c->{
//			System.out.println();
//		});
//		

	}

}
