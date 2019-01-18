package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
}
