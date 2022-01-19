package pay.my.buddy.dal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pay.my.buddy.entities.Client;

/**
 * DAL Interface that will allow interaction with external data sources
 * 
 * Interface used to define JpaRepository operations with the client table. It
 * extends the JpaRepository interface delivered by Spring Data JPA.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	/**
	 * findClientByEmail ** this method allows to select all attributes of client 
	 * by giving email
	 * 
	 * @param email
	 * @return Client object
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM Client WHERE email LIKE :x")
	public Optional<Client> findClientByEmail(@Param("x") String email);

}
