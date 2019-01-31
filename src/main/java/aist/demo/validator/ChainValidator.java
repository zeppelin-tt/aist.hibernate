package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.domain.Test;
import aist.demo.dto.ChainDto;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.ConflictException;
import aist.demo.exceptions.ConsistentModelException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.repository.ChainRepo;
import aist.demo.repository.ContourRepo;
import aist.demo.repository.TestRepo;
import aist.demo.repository.UserRepo;
import aist.demo.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validator
public class ChainValidator {

    private final ChainRepo chainRepo;
    private final ContourRepo contourRepo;
    private final UserRepo userRepo;
    private final TestRepo testRepo;
    private final CollectionValidator collectionValidator;


    @Autowired
    public ChainValidator(ChainRepo chainRepo, ContourRepo contourRepo, UserRepo userRepo, TestRepo testRepo, CollectionValidator collectionValidator) {
        this.chainRepo = chainRepo;
        this.contourRepo = contourRepo;
        this.userRepo = userRepo;
        this.testRepo = testRepo;
        this.collectionValidator = collectionValidator;
    }

    public void forSave(ChainDto dto) {
        ValidateUtil.instance
                .checkNonNull(dto.getId(), "Id генератора")
                .checkNull(dto.getContourId(), "Контур")
                .checkNull(dto.getCreatorId(), "Создатель генератора")
                .checkEmptyString(dto.getName(), "Имя генератора");
        if (chainRepo.existsByName(dto.getName())) {
            throw new ConflictException("Генератор с таким именем уже существует");
        }
        if (!contourRepo.existsById(dto.getContourId())) {
            throw new NotFoundException("Нет контура с id: " + dto.getContourId());
        }
        collectionValidator.validateAutomatedSystems(dto.getSystemIdSet(), false, false);
        if (!userRepo.existsById(dto.getCreatorId())) {
            throw new NotFoundException("Нет пользователя с id: " + dto.getContourId());
        }
        validateTests(dto);
        collectionValidator.validateGroups(dto.getGroupIdSet(), true, true);
    }

    public void forUpdate(ChainDto dto) {
        ValidateUtil.instance
                .checkEmptyString(dto.getName(), "Имя генератора")
                .checkNull(dto.getId(), "Id генератора");
        if (!chainRepo.existsById(dto.getId())) {
            throw new NotFoundException("Нет генератора с id: " + dto.getId());
        }
        collectionValidator.validateAutomatedSystems(dto.getSystemIdSet(),false, false);
        if (!userRepo.existsById(dto.getCreatorId())) {
            throw new NotFoundException("Нет пользователя с id: " + dto.getContourId());
        }
        if (!contourRepo.existsById(dto.getContourId())) {
            throw new NotFoundException("Нет контура с id: " + dto.getContourId());
        }
        validateTests(dto);
        collectionValidator.validateGroups(dto.getGroupIdSet(),true, true);
    }

    private void validateTests(ChainDto dto) {
        Integer[] testIdOrder = dto.getTestIdOrder();
        if (testIdOrder != null) {
            if (testIdOrder.length == 0) {
                throw new AistBaseException("Генератор должен включать хотя бы один тест");
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

}
