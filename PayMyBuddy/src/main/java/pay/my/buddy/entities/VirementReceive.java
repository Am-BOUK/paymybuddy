package pay.my.buddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * implementation of business object : virement receive that it is one of
 * operation type
 *
 * the virement receive data has two attribute : id sender and name sender
 */
@Entity
@DiscriminatorValue("E")
public class VirementReceive extends Operation {
	private Long idSender;
	private String nameSender;

	public VirementReceive(Date dateOperation, double amount, String description, Compte compte, Long idSender) {
		super(dateOperation, amount, description, compte);
		this.idSender = idSender;
	}

	public String getNameSender() {
		return nameSender;
	}

	public void setNameSender(String nameSender) {
		this.nameSender = nameSender;
	}

	public VirementReceive() {
		super();
	}

	public Long getIdSender() {
		return idSender;
	}

	public void setIdSender(Long idSender) {
		this.idSender = idSender;
	}

}
