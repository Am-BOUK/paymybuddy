package pay.my.buddy.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.entities.Compte;

@Service
public class CompteMetierImpl implements ICompteMetier {
	private static final Logger logger = LogManager.getLogger("CompteMetierImpl");

	@Autowired
	private CompteRepository compteRepository;

	@Override
	public Compte consulterCompte(Long idCompte) throws Exception {
		logger.info("find compte by idCompte : " + idCompte);
		try {
			Compte compte = compteRepository.findById(idCompte).orElse(null);
			logger.info("compte found : " + compte.getClient().getEmail());
			return compte;
		} catch (Exception e) {
			logger.error("Compte introuvable");
			throw new Exception("Compte introuvable");
		}

	}

	@Override
	public Compte findCompteByIdClient(Long idClient) throws Exception {
		logger.info("find compte by id client : " + idClient);
		try {
			Compte compte = (Compte) compteRepository.findCompteByIdClient(idClient);
			logger.info("compte found, id compte : " + compte.getIdCompte());
			return compte;

		} catch (Exception e) {
			logger.info("compte not found!");
			throw new Exception("compte not found!");
		}

	}

	@Override
	public Compte addNewCompte(Compte compte) throws Exception {
		logger.info("adding compte ");

		try {
			Compte compteAdded = compteRepository.save(compte);
			logger.info("compte added : " + compte.getIdCompte());
			return compteAdded;

		} catch (Exception e) {
			logger.info("can not addind compte, email is already exist " + compte.getClient().getEmail());
			throw new Exception("compte is already exist !");
		}
	}

	@Override
	public List<Compte> getAllComptes() throws Exception {
		try {
			List<Compte> getAllComptes = compteRepository.findAll();
			logger.info("getting liste of all comptes ");
			return getAllComptes;
		} catch (Exception e) {
			logger.info("can not getting list of all comptes");
			throw new Exception("can not getting list of all comptes !");
		}
	}

	@Override
	public void deleteCompteById(Long idCompte) throws Exception {
		logger.info("delete compte with id : " + idCompte);

		try {
			compteRepository.deleteById(idCompte);
			logger.info("delete compte with success !");

		} catch (Exception e) {
			logger.info("can not delete compte");
			throw new Exception("can not delete compte, id_compte does not exist !");
		}
	}

	@Override
	public void deleteCompteByIdClient(Long idClient) throws Exception {
		logger.info("delete compte by id client : " + idClient);
		try {
			Compte compteFound = findCompteByIdClient(idClient);
			compteRepository.delete(compteFound);
			logger.info("delete compte with success !");

		} catch (Exception e) {
			logger.info("can not delete compte");
			throw new Exception("can not delete compte, id_client does not exist !");
		}
	}

}