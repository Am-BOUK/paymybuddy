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
//	@Query(nativeQuery = true, value = "SELECT * FROM Client WHERE first_name Like :x")
	/*
	 * public Page<Client> findClientByName(@Param("x") String name, Pageable
	 * pageable);
	 * 
	 * @Query(nativeQuery = true, value =
	 * "SELECT * FROM Client WHERE email LIKE :x") public Optional<Client>
	 * findClientByFirstNameOrLAstName(@Param("x") String email);
	 */

	@Query(nativeQuery = true, value = "SELECT * FROM Client WHERE email LIKE :x")
	public Optional<Client> findClientByEmail(@Param("x") String email);

}
