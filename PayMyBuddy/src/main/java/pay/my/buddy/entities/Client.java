package pay.my.buddy.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Client implements Serializable {

	@Id
	@GeneratedValue
	@Column(length = 10)
	private Long idClient;
	@NotBlank(message = "first name is mandatory!")
	@Column(length = 25)
	private String firstName;
	@NotBlank(message = "last name is mandatory!")
	@Column(length = 25)
	private String lastName;
	@Email
	@NotBlank(message = "email is mandatory!")
	@Column(length = 25, unique = true)
	private String email;
	@NotBlank(message = "password is mandatory!")
	@Column(length = 25)
	private String password;
	@OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Compte compte;
	
//	@ManyToMany
//	private List<Client> cliens;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
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