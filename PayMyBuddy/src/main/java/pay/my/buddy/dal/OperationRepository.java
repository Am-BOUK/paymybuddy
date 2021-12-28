package pay.my.buddy.dal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pay.my.buddy.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {
//	@Query("select o from Operation o where o.compte.codeCompte=:x order by o.dateOperation desc")
	@Query(nativeQuery = true, value = "SELECT * FROM Operation WHERE ID_COMPTE LIKE :x")
	public Page<Operation> listOperation(@Param("x") Long idCompte, Pageable pageable);
	
//	@Query(nativeQuery = true, value = "SELECT * FROM Operation WHERE CODE_COMPTE_.Email_CLIENT LIKE :x")
//	public Page<Operation> listOperationByEmail(@Param("x") String email, Pageable pageable);

}