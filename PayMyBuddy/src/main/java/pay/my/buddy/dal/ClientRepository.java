package pay.my.buddy.dal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pay.my.buddy.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	@Query(nativeQuery = true, value = "SELECT * FROM Client WHERE first_name Like :x")
	public Page<Client> findClientByName(@Param("x") String name, Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT * FROM Client WHERE email LIKE :x")
	public Client findClientByFirstNameOrLAstName(@Param("x") String email);
	
	@Query(nativeQuery = true, value = "SELECT * FROM Client WHERE email LIKE :x")
	public Client findClientByEmail(@Param("x") String email);
	
	
	
//	public List<Client> findByFirstNameOrLastNameContains(String prenom, String nom);
}
