package pay.my.buddy.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.entities.Compte;

/**
 * implementation of compte business processing
 * 
 */
@Service
public class CompteMetierImpl implements ICompteMetier {
	private static final Logger logger = LogManager.getLogger("CompteMetierImpl");

	/**
	 * Calling operations of the compte repository
	 */
	@Autowired
	private CompteRepository compteRepository;

	/**
	 * consulter Compte ** This operation allows to check if the id of the compte we
	 * want to consult its information already exist in the data base, then allows
	 * to get it
	 * 
	 * @param idCompte the id of the compte we want to consult
	 * @return compte object if it exists
	 * @throws Exception
	 */
	@Override
	public Compte consulterCompte(Long idCompte) throws Exception {
		logger.info("find compte by idCompte : " + idCompte);
		Optional<Compte> compte = compteRepository.findById(idCompte);
		if (compte.isEmpty()) {
			logger.info("Compte introuvable");
			throw new Exception("Compte introuvable");
		}
		logger.info("compte found : " + compte.get().getClient().getEmail());
		return compte.get();
	}

	/**
	 * find Compte By Id Client ** This operation allows to check if the id of the
	 * client we want to find its compte is already exist in the data base, then
	 * allows to get it
	 * 
	 * @param idClient the id of the client we want to consult its compte
	 * @return compte object if it exists
	 * @throws Exception
	 */
	@Override
	public Compte findCompteByIdClient(Long idClient) throws Exception {
		logger.info("find compte by id client : " + idClient);
		Compte compte = compteRepository.findCompteByIdClient(idClient);
		if (compte == null) {
			logger.info("compte not found!");
			throw new Exception("compte not found!");
		}

		logger.info("compte found, id compte : " + compte.getIdCompte());
		return compte;
	}

	/**
	 * Add a new compte ** This operation allows to check if the id of the client we
	 * want to add its compte is already exist in the data base, then allows to add
	 * it
	 * 
	 * @param compte object to add
	 * @return compte object added
	 * @throws Exception
	 */
	@Override
	public Compte addNewCompte(Compte compte) throws Exception {
		logger.info("adding compte ");
		Compte compteFound = compteRepository.findCompteByIdClient(compte.getClient().getIdClient());
		if (compteFound != null) {
			logger.info("can not addind compte, email is already exist " + compte.getClient().getEmail());
			throw new Exception("compte is already exist !");
		}

		Compte compteAdded = compteRepository.save(compte);
		logger.info("compte added : " + compte.getIdCompte());
		return compteAdded;
	}

	@Override
	public Compte updateCompte(Compte compte) {
		return compteRepository.saveAndFlush(compte);

	}

	/**
	 * find Compte By Email Client ** This operation allows to check if the email of
	 * the client we want to find its compte is already exist in the data base, then
	 * allows to get it
	 * 
	 * @param email the email of the client we want to consult its compte
	 * @return compte object if it exists
	 * @throws Exception
	 */
	@Override
	public Compte findCompteByClientEmail(String email) throws Exception {
		logger.info("find compte by email : " + email);
		Optional<Compte> comptefound = compteRepository.findCompteByClientEmail(email);
		if (comptefound.isEmpty()) {
			throw new Exception("Compte n'existe pas !");
		}

		logger.info("Compte found : " + comptefound.get().getClient().getFirstName() + " "
				+ comptefound.get().getClient().getLastName());
		return comptefound.get();

	}

}
