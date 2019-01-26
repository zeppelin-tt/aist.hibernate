package aist.demo.repository;

import aist.demo.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {

    boolean existsByName(String name);

//    @Query(value = "SELECT c.id FROM tests t join chains c ON t.id = ANY(c.test_id_order) WHERE t.id = :testId", nativeQuery = true)
//    Set<Long> findChainsByTestId(@Param("token") Long testId);

}
