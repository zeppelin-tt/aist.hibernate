package aist.demo.hibernate.layers.repository;

import aist.demo.hibernate.domain.entity.Chain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChainRepo extends JpaRepository<Chain, Long> {

    boolean existsAllById(Set<Long> chainIdSet);

}
