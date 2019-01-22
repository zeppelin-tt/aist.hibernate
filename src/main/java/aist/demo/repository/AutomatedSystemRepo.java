package aist.demo.repository;

import aist.demo.domain.AutomatedSystem;
import aist.demo.domain.Contour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomatedSystemRepo extends JpaRepository<AutomatedSystem, Long> {
}
