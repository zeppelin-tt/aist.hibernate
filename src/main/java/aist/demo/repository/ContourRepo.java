package aist.demo.repository;

import aist.demo.domain.Contour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContourRepo extends JpaRepository<Contour, Long> {
    boolean existsByNameOrCode(String name, String code);
}
