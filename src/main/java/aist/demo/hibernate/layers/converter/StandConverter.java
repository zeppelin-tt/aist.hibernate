package aist.demo.hibernate.layers.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.entity.Chain;
import aist.demo.hibernate.domain.entity.Stand;
import aist.demo.hibernate.domain.model.object.StandModel;
import aist.demo.hibernate.layers.repository.ChainRepo;
import aist.demo.hibernate.layers.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class StandConverter {

    private final ChainRepo chainRepo;

    @Autowired
    public StandConverter(ChainRepo chainRepo) {
        this.chainRepo = chainRepo;
    }

    public Stand convert(StandModel model) {
        Stand stand = new Stand(model.getCode(), model.getName());
        Set<Long> chainIdSet = model.getChainIdSet();
        List<Chain> chainsById = chainRepo.findAllById(chainIdSet);
        stand.setChains(new HashSet<>(chainsById));
        stand.setId(model.getId());
        stand.setDescription(model.getDescription());
        return stand;
    }

    public StandModel convert(Stand stand) {
        StandModel model = new StandModel(stand.getCode(), stand.getName());
        Set<Long> chainIdSet = stand.getChains()
                .stream()
                .map(Chain::getId)
                .collect(Collectors.toSet());
        model.setChainIdSet(chainIdSet);
        model.setDescription(stand.getDescription());
        return model;
    }

}
