package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entity.Contour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContourRepo extends JpaRepository<Contour, Long> {
    boolean existsByNameOrCode(String name, String code);
}
