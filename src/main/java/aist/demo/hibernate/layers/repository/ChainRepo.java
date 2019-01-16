package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entry.Chain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ChainRepo extends JpaRepository<Chain, Long> {

    boolean existsAllById(Set<Long> chainIdSet);

}
