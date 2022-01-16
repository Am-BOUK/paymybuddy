package pay.my.buddy.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * 
 * implementation of business object: compte that will be manipulated by the
 * other layers.
 *
 * the compte data has five attribute : id compte, date creation, amount, client
 * and operations
 */
@Entity
public class Compte implements Serializable {
	@Id
	@GeneratedValue
	@Column(length = 10)
	private Long idCompte;
	@NotNull(message = "date of creation is mandatory!")
	private Date dateCreation;

	@Column(length = 10)
	private double amount;
	@OneToOne
	@NotNull(message = "Id_CLIENT is mandatory!")
	@JoinColumn(name = "Id_CLIENT")
	private Client client;
	@OneToMany(mappedBy = "compte")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Collection<Operation> operations;

	public Compte() {
		super();
	}

	public Compte(Date dateCreation, double amount, Client client) {
		super();
		this.dateCreation = dateCreation;
		this.amount = amount;
		this.client = client;
	}

	public Long getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(Long idCompte) {
		this.idCompte = idCompte;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

}
