package aist.demo.service;

import aist.demo.converter.TestConverter;
import aist.demo.domain.Test;
import aist.demo.dto.TestDto;
import aist.demo.exceptions.NotFoundException;
import aist.demo.repository.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TestService {

    private final TestRepo testRepo;
    private final TestConverter testConverter;

    @Autowired
    public TestService(TestRepo testRepo, TestConverter testConverter) {
        this.testRepo = testRepo;
        this.testConverter = testConverter;
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

    @Transactional
    public TestDto save(TestDto dto) {
        // TODO: 23.01.2019 не забыть валидатор
        Test test = testConverter.convert(dto);
        Test savedTest = testRepo.save(test);
        return testConverter.convert(savedTest);
    }

}
