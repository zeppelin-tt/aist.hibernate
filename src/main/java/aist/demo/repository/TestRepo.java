package aist.demo.repository;

import aist.demo.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {

    boolean existsByName(String name);

    @Query(value = "SELECT t.id, t.name " +
            "FROM tests t " +
            "JOIN chains c ON t.id = ANY (c.test_order) " +
            "JOIN LATERAL unnest(c.test_order) WITH ORDINALITY AS a (elem, nr) on t.id = a.elem " +
            "WHERE c.id = :id " +
            "ORDER BY nr", nativeQuery = true)
    List<Test> getTestsByChainId(@Param("id") Long id);

}
