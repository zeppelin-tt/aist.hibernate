package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entity.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandRepo extends JpaRepository<Stand, Long> {
    boolean existsByNameOrCode(String name, String code);
}
