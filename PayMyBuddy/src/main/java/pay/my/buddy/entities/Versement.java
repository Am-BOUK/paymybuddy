package pay.my.buddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * implementation of business object : versement that it is one of operation
 * type
 *
 */
@Entity
@DiscriminatorValue("V")
public class Versement extends Operation {

	public Versement() {
		super();
	}

	public Versement(Date dateOperation, double amount, String description, Compte compte) {
		super(dateOperation, amount, description, compte);
	}

}
