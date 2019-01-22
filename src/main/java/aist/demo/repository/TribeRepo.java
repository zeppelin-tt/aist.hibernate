package aist.demo.repository;

import aist.demo.domain.Tribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TribeRepo extends JpaRepository<Tribe, Long> {
    boolean existsByName(String name);
}
