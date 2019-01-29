package aist.demo.repository;

import aist.demo.domain.Chain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChainRepo extends JpaRepository<Chain, Long> , JpaSpecificationExecutor<Chain> {

    boolean existsAllById(Set<Long> chainIdSet);

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM chains c WHERE :id = ANY(c.test_id_order)", nativeQuery = true)
    Set<Chain> getChainsByTestId(@Param("id") Long id);

}
