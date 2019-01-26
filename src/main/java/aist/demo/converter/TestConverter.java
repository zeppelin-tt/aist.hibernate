package aist.demo.converter;

import aist.demo.annotate.Converter;
import aist.demo.domain.AutomatedSystem;
import aist.demo.domain.Chain;
import aist.demo.domain.Tag;
import aist.demo.domain.Test;
import aist.demo.dto.TestDto;
import aist.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Converter
public class TestConverter {

    private final UserRepo userRepo;
    private final ContourRepo contourRepo;
    private final AutomatedSystemRepo systemRepo;
    private final GroupRepo groupRepo;
    private final ChainRepo chainRepo;

    @Autowired
    public TestConverter(UserRepo userRepo, ContourRepo contourRepo, AutomatedSystemRepo systemRepo, GroupRepo groupRepo, ChainRepo chainRepo) {
        this.userRepo = userRepo;
        this.contourRepo = contourRepo;
        this.systemRepo = systemRepo;
        this.groupRepo = groupRepo;
        this.chainRepo = chainRepo;
    }

    public Test convert(TestDto dto) {
        Test test = new Test();
        test.setId(dto.getId());
        test.setName(dto.getName());
        test.setAvailability(dto.isAvailability());
        test.setType(dto.getType());
        test.setJobTrigger(dto.getJobTrigger());
        test.setLegacy(dto.isLegacy());
        test.setNeedAccountPool(dto.isNeedAccountPool());
        // TODO: 26.01.2019 здесь должны быть ваши теги!
        test.setSystem(systemRepo.getOne(dto.getSystemId()));
        test.setContour(contourRepo.getOne(dto.getContourId()));
        test.setCreator(userRepo.getOne(dto.getCreatorId()));
        return test;
    }

    public TestDto convert(Test test) {
        TestDto dto = new TestDto();
        dto.setId(test.getId());
        dto.setName(test.getName());
        dto.setAvailability(test.isAvailability());
        dto.setSystemId(test.getSystem().getId());
        dto.setContourId(test.getContour().getId());
        dto.setCreatorId(test.getCreator().getId());
        dto.setType(test.getType());
        // TODO: 26.01.2019 и здесь теги!
        dto.setNeedAccountPool(test.isNeedAccountPool());
        dto.setLegacy(test.isLegacy());
        dto.setJobTrigger(test.getJobTrigger());
        return dto;
    }

}
