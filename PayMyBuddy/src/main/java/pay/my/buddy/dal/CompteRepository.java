package pay.my.buddy.dal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pay.my.buddy.entities.Compte;

/**
 * DAL Interface that will allow interaction with external data sources
 * 
 * Interface used to define JpaRepository operations with the compte table. It
 * extends the JpaRepository interface delivered by Spring Data JPA.
 */
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
	@Query(nativeQuery = true, value = "SELECT * FROM Compte WHERE ID_CLIENT LIKE :x")
	public Compte findCompteByIdClient(@Param("x") Long idClient);

	public Optional<Compte> findCompteByClientEmail(String email);

}
