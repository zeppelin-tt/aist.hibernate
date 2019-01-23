package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.domain.AutomatedSystem;
import aist.demo.domain.Group;
import aist.demo.domain.Test;
import aist.demo.dto.ChainDto;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.ConflictException;
import aist.demo.exceptions.ConsistentModelException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validator
public class ChainValidator {

    private final ChainRepo chainRepo;
    private final ContourRepo contourRepo;
    private final AutomatedSystemRepo systemRepo;
    private final UserRepo userRepo;
    private final TestRepo testRepo;
    private final GroupRepo groupRepo;

    @Autowired
    public ChainValidator(ChainRepo chainRepo, ContourRepo contourRepo, AutomatedSystemRepo systemRepo, UserRepo userRepo, TestRepo testRepo, GroupRepo groupRepo) {
        this.chainRepo = chainRepo;
        this.contourRepo = contourRepo;
        this.systemRepo = systemRepo;
        this.userRepo = userRepo;
        this.testRepo = testRepo;
        this.groupRepo = groupRepo;
    }

    public void forSave(ChainDto dto) {
        if (dto.getId() != null) {
            throw new AistBaseException("Генератор для сохранения имеет id");
        }
        if (dto.getContourId() == null) {
            throw new NotFoundException("Не указан контур цепочки" );
        }
        if (dto.getUserId() == null) {
            throw new NotFoundException("Не указан создатель цепочки" );
        }
        if (chainRepo.existsByName(dto.getName())) {
            throw new ConflictException("Генератор с таким именем или кодом уже существует");
        }
        if (!contourRepo.existsById(dto.getContourId())) {
            throw new NotFoundException("Нет контура с id: " + dto.getContourId());
        }
        validateAutomatedSystems(dto);
        if (!userRepo.existsById(dto.getUserId())) {
            throw new NotFoundException("Нет пользователя с id: " + dto.getContourId());
        }
        validateTests(dto);
        validateGroups(dto);
    }

    public void forUpdate(ChainDto dto) {
        if (dto.getId() == null) {
            throw new AistBaseException("Генератор для редактирования не имеет id");
        }
        if (!chainRepo.existsById(dto.getId())) {
            throw new NotFoundException("Нет генератора с id: " + dto.getId());
        }
        validateAutomatedSystems(dto);
        if (!userRepo.existsById(dto.getUserId())) {
            throw new NotFoundException("Нет пользователя с id: " + dto.getContourId());
        }
        if (!contourRepo.existsById(dto.getContourId())) {
            throw new NotFoundException("Нет контура с id: " + dto.getContourId());
        }
        validateTests(dto);
        validateGroups(dto);
    }


    private void validateAutomatedSystems(ChainDto dto) {
        Set<Long> systemIdSet = dto.getSystems();
        if (systemIdSet != null) {
            if (systemIdSet.isEmpty()) {
                // TODO: 23.01.2019 переделать логику!
                throw new AistBaseException("Цепочка должна включать хотя бы одну систему");
            } else {
                Set<AutomatedSystem> dbAutomatedSystems = new HashSet<>(systemRepo.findAllById(systemIdSet));
                if (dbAutomatedSystems.size() != systemIdSet.size()) {
                    Set<Long> idInDb = dbAutomatedSystems
                            .stream()
                            .map(AutomatedSystem::getId)
                            .collect(Collectors.toSet());
                    systemIdSet.removeAll(idInDb);
                    throw new ConsistentModelException("В БД нет следующих id АС: " + systemIdSet.toString());
                }
            }
        }
    }

    private void validateTests(ChainDto dto) {
        Integer[] testIdOrder = dto.getTestIdOrder();
        if (testIdOrder != null) {
            if (testIdOrder.length == 0) {
                throw new AistBaseException("Цепочка должна включать хотя бы один тест");
            } else {
                List<Long> listTestIdOrder = Arrays.stream(testIdOrder)
                        .mapToLong(i -> i)
                        .boxed()
                        .collect(Collectors.toList());
                List<Test> dbTests = testRepo.findAllById(listTestIdOrder);
                if (dbTests.size() != listTestIdOrder.size()) {
                    Set<Long> idInDb = dbTests
                            .stream()
                            .map(Test::getId)
                            .collect(Collectors.toSet());
                    listTestIdOrder.removeAll(idInDb);
                    throw new ConsistentModelException("В БД нет следующих id Тестов: " + listTestIdOrder.toString());
                }
            }
        }
    }

    private void validateGroups(ChainDto dto) {
        Set<Long> groupIdSet = dto.getGroupIdSet();
        if (groupIdSet != null && !groupIdSet.isEmpty()) {
            Set<Group> dbGroups = new HashSet<>(groupRepo.findAllById(groupIdSet));
            if (dbGroups.size() != groupIdSet.size()) {
                Set<Long> idInDb = dbGroups
                        .stream()
                        .map(Group::getId)
                        .collect(Collectors.toSet());
                groupIdSet.removeAll(idInDb);
                throw new ConsistentModelException("В БД нет следующих id Тестов: " + groupIdSet.toString());
            }
        }
    }

}
