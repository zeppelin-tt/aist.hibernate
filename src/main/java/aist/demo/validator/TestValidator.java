package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.domain.Group;
import aist.demo.dto.TestDto;
import aist.demo.dto.json.JobTrigger;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.ConflictException;
import aist.demo.exceptions.ConsistentModelException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.repository.*;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Validator
public class TestValidator {

    private final ChainRepo chainRepo;
    private final ContourRepo contourRepo;
    private final AutomatedSystemRepo systemRepo;
    private final UserRepo userRepo;
    private final TestRepo testRepo;
    private final GroupRepo groupRepo;

    @Autowired
    public TestValidator(ChainRepo chainRepo, ContourRepo contourRepo, AutomatedSystemRepo systemRepo, UserRepo userRepo, TestRepo testRepo, GroupRepo groupRepo) {
        this.chainRepo = chainRepo;
        this.contourRepo = contourRepo;
        this.systemRepo = systemRepo;
        this.userRepo = userRepo;
        this.testRepo = testRepo;
        this.groupRepo = groupRepo;
    }

    public void forSave(TestDto dto) {
        if (dto.getId() != null) {
            throw new AistBaseException("Тест для сохранения имеет id");
        }
        if (dto.getContourId() == null) {
            throw new NotFoundException("Не указан контур теста" );
        }
        if (dto.getCreatorId() == null) {
            throw new NotFoundException("Не указан создатель теста" );
        }
        if (dto.getSystemId() == null) {
            throw new NotFoundException("Не указана автоматизированная система для теста" );
        }
        if(dto.getName() == null || dto.getName().isEmpty()) {
            throw new NotFoundException("Не указано имя для Теста" );
        }
        JobTrigger jobTrigger = dto.getJobTrigger();
        if (jobTrigger == null) {
            throw new NotFoundException("Не указаны данные для Jenkins" );
        }
        checkJobURL(jobTrigger);
        if (testRepo.existsByName(dto.getName())) {
            throw new ConflictException("Тест с таким именем уже существует");
        }
        checkExistsContours(dto);
        checkExistsUsers(dto);
        checkExistsSystems(dto);
        validateGroups(dto);
    }

    public void forUpdate(TestDto dto) {
        if (dto.getId() == null) {
            throw new AistBaseException("Тест для редактирования не имеет id");
        }
        if (!testRepo.existsById(dto.getId())) {
            throw new NotFoundException("Нет теста с id: " + dto.getId());
        }
        if (dto.getContourId() != null) {
            checkExistsContours(dto);
        }
        if (dto.getCreatorId() != null) {
            checkExistsUsers(dto);
        }
        if (dto.getSystemId() != null) {
            checkExistsSystems(dto);
        }
        validateGroups(dto);
    }

    private void checkExistsSystems(TestDto dto) {
        if (!systemRepo.existsById(dto.getSystemId())) {
            throw new NotFoundException("Нет автоматизированной системы с id: " + dto.getSystemId());
        }
    }

    private void checkExistsUsers(TestDto dto) {
        if (!userRepo.existsById(dto.getCreatorId())) {
            throw new NotFoundException("Нет пользователя с id: " + dto.getCreatorId());
        }
    }

    private void checkExistsContours(TestDto dto) {
        if (!contourRepo.existsById(dto.getContourId())) {
            throw new NotFoundException("Нет контура с id: " + dto.getContourId());
        }
    }

    private void validateGroups(TestDto dto) {
        Set<Long> groupIdSet = dto.getGroups();
        if (groupIdSet != null && !groupIdSet.isEmpty()) {
            Set<Group> dbGroups = new HashSet<>(groupRepo.findAllById(groupIdSet));
            if (dbGroups.size() != groupIdSet.size()) {
                Set<Long> idInDb = dbGroups
                        .stream()
                        .map(Group::getId)
                        .collect(Collectors.toSet());
                throw new ConsistentModelException("В БД нет следующих id Групп: " + Sets.difference(groupIdSet, idInDb));
            }
        }
    }

    // TODO: 26.01.2019 переделать запрос под спринговый
    private void checkJobURL(JobTrigger jobTrigger) throws AistBaseException {
//        String buildUrl = jobTrigger.getJobUrl()  + "/build?token=" + jobTrigger.getPassOrToken();
//        if (!StringUtils.isEmpty(jobTrigger.getUserLogin()))
//            buildUrl = jobTrigger.getJobUrl()  + "/build";
//        int responseCode = HttpUtils.fireGetRequest(buildUrl, jobTrigger.getUserLogin(), jobTrigger.getPassOrToken());
//        checkResponseCode(responseCode);
    }

    private void checkResponseCode(int code) {
        switch(code) {
            case 200:
            case 405:
                return;
            case 401:
                throw new AistBaseException("Указана ссылка на отключенный Jenkins job");
            case 404:
                throw new AistBaseException(
                        "Нет доступа до Jenkins job. Проверьте настройки доступа для анонимного/авторизованного пользователя");
            case 409:
                throw new AistBaseException("Указана ссылка на отключенный Jenkins job");
            default:
                throw new AistBaseException("Необходимо указывать ссылку на доступный действующий Jenkins job");
        }
    }


}
