package pay.my.buddy.dal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pay.my.buddy.entities.Operation;

/**
 * DAL Interface that will allow interaction with external data sources
 * 
 * Interface used to define JpaRepository operations with the operation table.
 * It extends the JpaRepository interface delivered by Spring Data JPA.
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
	@Query(nativeQuery = true, value = "SELECT * FROM Operation WHERE ID_COMPTE LIKE :x ORDER BY date_operation DESC")
	public Page<Operation> listOperation(@Param("x") Long idCompte, Pageable pageable);

}
