package aist.demo.service;

import aist.demo.converter.GroupConverter;
import aist.demo.domain.Contour;
import aist.demo.domain.Group;
import aist.demo.dto.ChainDto;
import aist.demo.dto.ContourDto;
import aist.demo.dto.GroupDto;
import aist.demo.repository.GroupRepo;
import aist.demo.repository.UserRepo;
import aist.demo.validator.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepo groupRepo;
    private final GroupConverter groupConverter;
    private final GroupValidator groupValidator;

    @Autowired
    public GroupService(GroupRepo groupRepo, GroupConverter groupConverter, GroupValidator groupValidator) {
        this.groupRepo = groupRepo;
        this.groupConverter = groupConverter;
        this.groupValidator = groupValidator;
    }


    public Set<GroupDto> findAll() {
        return groupRepo.findAll().stream()
                .map(groupConverter::convert)
                .collect(Collectors.toSet());
    }

    @Transactional
    public GroupDto save(GroupDto dto) {
        groupValidator.forSave(dto);
        Group group = groupConverter.convert(dto);
        Group savedGroup = groupRepo.save(group);
        return groupConverter.convert(savedGroup);
    }

}
