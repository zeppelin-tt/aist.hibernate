package aist.demo.converter;

import aist.demo.annotate.Converter;
import aist.demo.dto.ContourDto;
import aist.demo.domain.Chain;
import aist.demo.domain.Contour;
import aist.demo.repository.ChainRepo;
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

    public Contour convert(ContourDto dto) {
        Contour contour = new Contour(dto.getCode(), dto.getName());
        Set<Long> chainIdSet = dto.getChainIdSet();
        List<Chain> chainsById = chainRepo.findAllById(chainIdSet);
        contour.setChains(new HashSet<>(chainsById));
        contour.setId(dto.getId());
        contour.setDescription(dto.getDescription());
        return contour;
    }

    public ContourDto convert(Contour contour) {
        ContourDto dto = new ContourDto(contour.getCode(), contour.getName());
        Set<Long> chainIdSet = contour.getChains()
                .stream()
                .map(Chain::getId)
                .collect(Collectors.toSet());
        dto.setChainIdSet(chainIdSet);
        dto.setDescription(contour.getDescription());
        return dto;
    }

}
