package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entry.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, Long> {
}
