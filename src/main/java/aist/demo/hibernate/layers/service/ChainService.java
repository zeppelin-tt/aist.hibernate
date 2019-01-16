package aist.demo.hibernate.layers.service;

import aist.demo.hibernate.annotate.StorageLayer;
import aist.demo.hibernate.domain.entry.Chain;
import aist.demo.hibernate.layers.repository.ChainRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@StorageLayer
public class ChainService {

    private final ChainRepo chainRepo;

    @Autowired
    public ChainService(ChainRepo chainRepo) {
        this.chainRepo = chainRepo;
    }

    public Set<Chain> findAllById(Set<Long> chainIdSet) {
        return new HashSet<>(chainRepo.findAllById(chainIdSet));
    }

    public boolean existsAllById(Set<Long> chainIdSet) {
        return chainRepo.existsAllById(chainIdSet);
    }


}
