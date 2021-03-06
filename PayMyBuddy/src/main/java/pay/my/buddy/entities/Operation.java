package pay.my.buddy.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 
 * implementation of business object: operation that will be manipulated by the
 * other layers.
 *
 * the person data is abstract class and has six attribute : id operation, date
 * operation, amount, description, compte and facturation
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_OP", discriminatorType = DiscriminatorType.STRING, length = 3)
@JsonDeserialize(as = Versement.class)
public abstract class Operation implements Serializable {
	@Id
	@GeneratedValue
	@Column(length = 10)
	private Long idOperation;
	@NotNull(message = "date of operation is mandatory!")
	private Date dateOperation;
	@NotNull(message = "amount is mandatory!")
	@Column(length = 25)
	private double amount;
	@NotNull(message = "description is mandatory!")
	@Column(length = 225)
	private String description;
	@NotNull(message = "ID_COMPTE is mandatory!")
	@ManyToOne
	@JoinColumn(name = "ID_COMPTE")
	private Compte compte;
	@Column(length = 10)
	private double facturation;

	public Operation() {
		super();
	}

	public Operation(Date dateOperation, double amount, String description, Compte compte) {
		super();
		this.dateOperation = dateOperation;
		this.amount = amount;
		this.description = description;
		this.compte = compte;
	}

	public double getFacturation() {
		return facturation;
	}

	public void setFacturation(double facturation) {
		this.facturation = facturation;
	}

	public Long getIdOperation() {
		return idOperation;
	}

	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
	}

	public Date getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
