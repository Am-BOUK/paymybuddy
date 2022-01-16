package pay.my.buddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * implementation of business object : virement sent that it is one of
 * operation type
 *
 * the virement sent data has two attribute : id recipient and name recipient
 */
@Entity
@DiscriminatorValue("S")
public class VirementSent extends Operation{
	private Long idRecipient;
	private String nameRecipient;

	public String getNameRecipient() {
		return nameRecipient;
	}

	public void setNameRecipient(String nameRecipient) {
		this.nameRecipient = nameRecipient;
	}

	public VirementSent() {
		super();
	}

	public Long getIdRecipient() {
		return idRecipient;
	}

	public void setIdRecipient(Long idRecipient) {
		this.idRecipient = idRecipient;
	}

	public VirementSent(Date dateOperation, double amount, String description, Compte compte, Long idRecipient) {
		super(dateOperation, amount, description, compte);
		this.idRecipient = idRecipient;
	}
	
	
	
	

}
