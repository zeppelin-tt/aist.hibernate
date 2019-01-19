package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entity.TribeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TribeCommandRepo extends JpaRepository<TribeCommand, Long> {
    boolean existsByName(String name);
    Set<TribeCommand> findByTribeId(Long tribeId);
}
