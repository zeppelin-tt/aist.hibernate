package aist.demo.service;

import aist.demo.converter.TestConverter;
import aist.demo.domain.Test;
import aist.demo.dto.TestDto;
import aist.demo.exceptions.NotFoundException;
import aist.demo.repository.TestRepo;
import aist.demo.validator.TestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TestService {

    private final TestRepo testRepo;
    private final TestConverter testConverter;
    private final TestValidator testValidator;

    @Autowired
    public TestService(TestRepo testRepo, TestConverter testConverter, TestValidator testValidator) {
        this.testRepo = testRepo;
        this.testConverter = testConverter;
        this.testValidator = testValidator;
    }

    public TestDto find(Long id) {
        Test test = testRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Test by id: " + id));
        return testConverter.convert(test);
    }

    public Set<TestDto> findAll() {
        return testRepo.findAll().stream()
                .map(testConverter::convert)
                .collect(Collectors.toSet());
    }

//    public Set<TestDto> getByFilter(TestDto dto) {
//
//    }

    @Transactional
    public TestDto save(TestDto dto) {
        testValidator.forSave(dto);
        Test test = testConverter.convert(dto);
        Test saved = testRepo.save(test);
        return testConverter.convert(saved);
    }

    @Transactional
    public void update(TestDto dto) {
        testValidator.forUpdate(dto);
        Test test = testConverter.convert(dto);
        testRepo.save(test);
    }

    @Transactional
    public void delete(Long id) {
        testValidator.forDelete(id);
        testRepo.deleteById(id);
    }

}
