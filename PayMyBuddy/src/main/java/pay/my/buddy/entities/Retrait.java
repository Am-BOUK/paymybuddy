package pay.my.buddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * implementation of business object : retrait that it is one of operation type
 * 
 */
@Entity
@DiscriminatorValue("R")
public class Retrait extends Operation {

	public Retrait() {
		super();
	}

	public Retrait(Date dateOperation, double amount, String description, Compte compte) {
		super(dateOperation, amount, description, compte);
	}

}
