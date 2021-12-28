package pay.my.buddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("E")
public class VirementReceive extends Operation{
	private Long idSender;
	private String nameSender;

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

	public VirementReceive(Date dateOperation, double amount, String description, Compte compte, Long idSender) {
		super(dateOperation, amount, description, compte);
		this.idSender = idSender;
	}
	
	
	
	

}
