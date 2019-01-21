package aist.demo.hibernate.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.Chain;
import aist.demo.hibernate.domain.Contour;
import aist.demo.hibernate.dto.ContourDto;
import aist.demo.hibernate.repository.ChainRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class ContourConverter {

    private final ChainRepo chainRepo;

    @Autowired
    public ContourConverter(ChainRepo chainRepo) {
        this.chainRepo = chainRepo;
    }

    public Contour convert(ContourDto model) {
        Contour contour = new Contour(model.getCode(), model.getName());
        Set<Long> chainIdSet = model.getChainIdSet();
        List<Chain> chainsById = chainRepo.findAllById(chainIdSet);
        contour.setChains(new HashSet<>(chainsById));
        contour.setId(model.getId());
        contour.setDescription(model.getDescription());
        return contour;
    }

    public ContourDto convert(Contour contour) {
        ContourDto model = new ContourDto(contour.getCode(), contour.getName());
        Set<Long> chainIdSet = contour.getChains()
                .stream()
                .map(Chain::getId)
                .collect(Collectors.toSet());
        model.setChainIdSet(chainIdSet);
        model.setDescription(contour.getDescription());
        return model;
    }

}
