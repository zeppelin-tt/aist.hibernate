package aist.demo.hibernate.layers.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.entry.Stand;
import aist.demo.hibernate.domain.model.object.StandModel;
import aist.demo.hibernate.exceptions.ConsistentModelException;
import aist.demo.hibernate.layers.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Converter
public class StandConverter {

    private final ChainService chainService;

    @Autowired
    public StandConverter(ChainService chainService) {
        this.chainService = chainService;
    }

    public Stand convert(StandModel standModel) {
        Set<Long> chainIdSet = standModel.getChainIdSet();
        Stand stand = new Stand(standModel.getCode(), standModel.getName());
        if (chainIdSet != null && !chainIdSet.isEmpty()) {
            if(!chainService.existsAllById(chainIdSet)) {
                throw new ConsistentModelException("Not all chainId exists in DB!");
            }
            stand.setChains(chainService.findAllById(chainIdSet));
        }
        stand.setId(standModel.getId());
        stand.setDescription(standModel.getDescription());
        return stand;
    }

}
