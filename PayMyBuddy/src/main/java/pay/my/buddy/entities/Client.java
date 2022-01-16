package pay.my.buddy.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * 
 * implementation of business object: Client that will be manipulated by the
 * other layers.
 *
 * the client data has seven attribute : id client, first name,, last name,
 * email, password, compte and connections list
 */

@Entity
public class Client implements Serializable {

	@Id
	@GeneratedValue
	@Column(length = 20)
	private Long idClient;

	@NotEmpty(message = "First name cannot be empty.")
	@Column(length = 25)
	private String firstName;

	@NotEmpty(message = "Last name cannot be empty.")
	@Column(length = 25)
	private String lastName;

	@Email
	@NotEmpty(message = "Email cannot be empty.")
	@Column(length = 25, unique = true)
	private String email;

	@NotBlank(message = "password cannot be empty.")
	@Column(length = 255)
	private String password;

	@OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Compte compte;

	@ManyToMany
	@JoinTable(name = "tbl_connections", joinColumns = @JoinColumn(name = "clientId"), inverseJoinColumns = @JoinColumn(name = "connectionId"))
	private List<Client> connections;

	public List<Client> getConnections() {
		return connections;
	}

	public void setConnections(List<Client> connections) {
		this.connections = connections;
	}

	public Client() {
		super();
	}

	public Client(@NotNull(message = "first name is mandatory!") String firstName,
			@NotNull(message = "last name is mandatory!") String lastName,
			@Email @NotNull(message = "email is mandatory!") String email,
			@NotNull(message = "password is mandatory!") String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.connections = new ArrayList<Client>();
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

}
