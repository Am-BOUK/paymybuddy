package pay.my.buddy.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pay.my.buddy.constants.Fare;
import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.dal.OperationRepository;
import pay.my.buddy.entities.Compte;
import pay.my.buddy.entities.Operation;
import pay.my.buddy.entities.Retrait;
import pay.my.buddy.entities.Versement;
import pay.my.buddy.entities.VirementReceive;
import pay.my.buddy.entities.VirementSent;

@Service
@Transactional
public class OperationMetrierImpl implements IOperationMetier {
	private static final Logger logger = LogManager.getLogger("PayMyBuddyMetrierImpl");
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private ICompteMetier compteMetier;



	@Override
	public void verser(Long idCompte, double montant, String description) throws Exception {
		logger.info("verser dans le compte numéro : " + idCompte);
		Compte compte = compteMetier.consulterCompte(idCompte);
		Versement v = new Versement(new Date(), montant, description, compte);
		operationRepository.save(v);
		double facturation = montant * Fare.FACTURATION;
		v.setFacturation(facturation);
		compte.setAmount(compte.getAmount() + montant - facturation);
//		compte.setFacturation(compte.getFacturation() + facturation);
		compteRepository.save(compte);
		logger.info("versement effectué avec succée");

	}

	@Override
	public void retirer(Long idCompte, double montant, String description) throws Exception {
		logger.info("retirer du compte numéro : " + idCompte);
		Compte compte = compteMetier.consulterCompte(idCompte);
		double facturation = montant * Fare.FACTURATION;
		if (compte.getAmount() < montant + facturation) {
			throw new Exception("Solde insuffisant!");
		}
		Retrait r = new Retrait(new Date(), montant, description, compte);
		r.setFacturation(facturation);
		operationRepository.save(r);
		compte.setAmount(compte.getAmount() - montant - facturation);
//		compte.setFacturation(compte.getFacturation() + facturation);
		compteRepository.save(compte);
		logger.info("retrait effectué avec succée");

	}

	@Override
	public void virement(Long idSender, Long idRecipient, double montant, String description) throws Exception {
		logger.info("virement du compte numéro : " + idSender + " vers le compte numéro : " + idRecipient);

		if (idSender.equals(idRecipient)) {
			throw new Exception("Opération impossible : virement sur le même compte");
		}
		Compte compteSender = compteMetier.consulterCompte(idSender);
		double facturation = montant * Fare.FACTURATION;

		if (compteSender.getAmount() < montant + facturation) {
			throw new RuntimeException("Solde insuffisant!");
		}

		compteSender.setAmount(compteSender.getAmount() - montant - facturation);
		compteRepository.save(compteSender);

		Compte compteRecipient = compteMetier.consulterCompte(idRecipient);

		compteRecipient.setAmount(compteRecipient.getAmount() + montant);
		compteRepository.save(compteRecipient);

		VirementSent virementSent = new VirementSent(new Date(), montant, description, compteSender, idRecipient);
		VirementReceive virementReceive = new VirementReceive(new Date(), montant, description, compteRecipient,
				idSender);
		virementSent.setFacturation(facturation);
		virementSent.setNameRecipient(compteRecipient.getClient().getLastName());
//		virementReceive.setFacturation(facturation);
		virementReceive.setNameSender(compteSender.getClient().getLastName());
		operationRepository.save(virementSent);
		operationRepository.save(virementReceive);
		logger.info("virement effectué avec succée");

	}

	@Override
	public Page<Operation> listOperation(Long idCompte, int page, int size) {
		logger.info("liste des opération par page");
		return operationRepository.listOperation(idCompte, PageRequest.of(page, size));

	}

}