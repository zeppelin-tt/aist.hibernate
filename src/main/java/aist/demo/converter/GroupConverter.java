package aist.demo.converter;

import aist.demo.annotate.Converter;
import aist.demo.domain.Chain;
import aist.demo.domain.Group;
import aist.demo.dto.GroupDto;
import aist.demo.repository.ChainRepo;
import aist.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class GroupConverter {

    private final ChainRepo chainRepo;
    private final UserRepo userRepo;

    @Autowired
    public GroupConverter(ChainRepo chainRepo, UserRepo userRepo) {
        this.chainRepo = chainRepo;
        this.userRepo = userRepo;
    }

    public Group convert(GroupDto dto) {
        Group group = new Group();
        group.setId(dto.getId());
        group.setName(dto.getName());
        group.setCreatedByUser(userRepo.getOne(dto.getCreatedByUserId()));
        Set<Chain> chains = dto.getChainIdSet() == null ?
                Collections.emptySet() :
                new HashSet<>(chainRepo.findAllById(dto.getChainIdSet()));
        group.setChains(chains);
        return group;
    }

    public GroupDto convert(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setCreatedByUserId(group.getCreatedByUser().getId());
        Set<Long> chainIdSet = group.getChains() == null ?
                Collections.emptySet() :
                group.getChains()
                .stream()
                .map(Chain::getId)
                .collect(Collectors.toSet());
        dto.setChainIdSet(chainIdSet);
        return dto;
    }

}
