package pay.my.buddy.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pay.my.buddy.constants.Fare;
import pay.my.buddy.constants.Rounding;
import pay.my.buddy.dal.CompteRepository;
import pay.my.buddy.dal.OperationRepository;
import pay.my.buddy.entities.Compte;
import pay.my.buddy.entities.Operation;
import pay.my.buddy.entities.Retrait;
import pay.my.buddy.entities.Versement;
import pay.my.buddy.entities.VirementReceive;
import pay.my.buddy.entities.VirementSent;

/**
 * Implementing the management of interactions between the application user and
 * the application.
 *
 * The class is annotated with @Transactional, rolling back every transactions
 * in case of any Exceptions thrown by the different methods.
 */
@Service
@Transactional
public class OperationMetrierImpl implements IOperationMetier {
	private static final Logger logger = LogManager.getLogger("OperationMetrierImpl");

	/**
	 * Calling operations of the operation repository
	 */
	@Autowired
	private OperationRepository operationRepository;

	/**
	 * Calling operations of the compte repository
	 */
	@Autowired
	private CompteRepository compteRepository;

	/**
	 * verser ** Method used to make a payment to account.
	 *
	 * this operation allows to check if the account is present in the database,
	 * Then it proceed updated it by adding money to the account amount in the
	 * database
	 * 
	 * @param idCompte    : id of the count
	 * @param money       : amount to be paid into the account
	 * @param description : the description of the payment operation
	 * @throws Exception
	 */
	@Override
	public Compte verser(Long idCompte, double money, String description) throws Exception {
		logger.info("verser dans le compte num??ro : " + idCompte);
		if (money == 0 || description.isBlank()) {
			throw new Exception("Amount and description are mandatory, they can't be null !");
		}

		Optional<Compte> compte = compteRepository.findById(idCompte);
		Versement versement = new Versement(new Date(), money, description, compte.get());
		double facturation = money * Fare.FACTURATION;
		versement.setFacturation(facturation);
		operationRepository.save(versement);
		double amount = compte.get().getAmount() + money - facturation;
		BigDecimal bigDecimal = new BigDecimal(amount).setScale(Rounding.DECIMAL, RoundingMode.FLOOR);
		double amountArounded = bigDecimal.doubleValue();
		compte.get().setAmount(amountArounded);
		compteRepository.save(compte.get());
		logger.info("versement effectue avec succee");

		return compte.get();

	}

	/**
	 * retirer ** Method used to withdraw money from the account.
	 *
	 * this operation allows to check if the account is present in the database,
	 * Then it proceed updated it by withdrawing the money from the account amount
	 * in the database
	 * 
	 * @param idCompte    : id of the count
	 * @param money       : amount to be withdrawn from the account
	 * @param description : the description of the payment operation
	 * @return
	 * @throws Exception
	 */
	@Override
	public Compte retirer(Long idCompte, double money, String description) throws Exception {
		logger.info("retirer du compte num??ro : " + idCompte);
		if (money == 0 || description.isBlank()) {
			throw new Exception("Amount and description are mandatory, they can't be null !");
		}
		Optional<Compte> compte = compteRepository.findById(idCompte);
		double facturation = money * Fare.FACTURATION;
		if (compte.get().getAmount() < money + facturation) {
			throw new Exception("Solde insuffisant!");
		}
		Retrait retrait = new Retrait(new Date(), money, description, compte.get());
		retrait.setFacturation(facturation);
		operationRepository.save(retrait);
		double amount = compte.get().getAmount() - money - facturation;
		BigDecimal bigDecimal = new BigDecimal(amount).setScale(Rounding.DECIMAL, RoundingMode.FLOOR);
		double amountArounded = bigDecimal.doubleValue();
		compte.get().setAmount(amountArounded);
		compteRepository.save(compte.get());
		logger.info("retrait effectu?? avec succ??e");
		return compte.get();
	}

	/**
	 * virement ** The method used to make a transfer from the account to another
	 * account. this operation allows to check if both accounts are present in the
	 * database, Then it proceeds to update both accounts by withdrawing the money
	 * from the amount of the sending account and adding the money to the amount of
	 * the receiving account in the database
	 * 
	 * @param idSender      : id of the sending account
	 * @param emailReceiper : email of the receiving account
	 * @param money         : amount to be withdrawn from the sending account and
	 *                      adding to the receiving account
	 * @param description   : the description of the transfer operation
	 * @return
	 * @throws Exception
	 */
	@Override
	public Compte virement(Long idSender, String emailReceiper, double money, String description) throws Exception {
		logger.info("virement du compte num??ro : " + idSender + " vers le compte de : " + emailReceiper);

		Optional<Compte> compteSender = compteRepository.findById(idSender);
		Optional<Compte> compteRecipient = compteRepository.findCompteByClientEmail(emailReceiper);
		if (idSender.equals(compteRecipient.get().getIdCompte())) {
			throw new Exception("Operation impossible : virement sur le meme compte");
		}
		double facturation = money * Fare.FACTURATION;

		if (compteSender.get().getAmount() < money + facturation) {
			throw new Exception("Solde insuffisant!");
		}

		double amountSender = compteSender.get().getAmount() - money - facturation;
		BigDecimal bigDecimal = new BigDecimal(amountSender).setScale(Rounding.DECIMAL, RoundingMode.FLOOR);
		double amountSenderArounded = bigDecimal.doubleValue();
		compteSender.get().setAmount(amountSenderArounded);
		compteRepository.save(compteSender.get());

		double amountRecipient = compteRecipient.get().getAmount() + money;
		bigDecimal = new BigDecimal(amountRecipient).setScale(Rounding.DECIMAL, RoundingMode.FLOOR);
		double amountRecipientArounded = bigDecimal.doubleValue();
		compteRecipient.get().setAmount(amountRecipientArounded);
		compteRepository.save(compteRecipient.get());

		VirementSent virementSent = new VirementSent(new Date(), money, description, compteSender.get(),
				compteRecipient.get().getIdCompte());
		VirementReceive virementReceive = new VirementReceive(new Date(), money, description, compteRecipient.get(),
				idSender);
		virementSent.setFacturation(facturation);
		virementSent.setNameRecipient(compteRecipient.get().getClient().getLastName());
		virementReceive.setNameSender(compteSender.get().getClient().getLastName());

		operationRepository.save(virementSent);
		operationRepository.save(virementReceive);
		logger.info("virement effectu?? avec succ??e");
		return compteSender.get();

	}

	@Override
	public Page<Operation> listOperation(Long idCompte, int page, int size) {
		logger.info("liste des op??ration par page");
		return operationRepository.listOperation(idCompte, PageRequest.of(page, size));
	}

}
