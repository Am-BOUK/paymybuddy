package pay.my.buddy.dal;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pay.my.buddy.entities.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
	@Query(nativeQuery = true, value = "SELECT * FROM Compte WHERE ID_CLIENT LIKE :x")
	public Compte findCompteByIdClient(@Param("x") Long idClient);
	
//	@Query("select o from Operation o where o.compte.codeCompte=:x
//	@Query(nativeQuery = true, value = "SELECT * FROM Operation WHERE CODE_COMPTE_.Email_CLIENT LIKE :x")
//	public Page<Operation> listOperationByEmail(@Param("x") String email, Pageable pageable);
}
