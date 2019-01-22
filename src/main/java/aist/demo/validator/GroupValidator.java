package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.dto.GroupDto;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.ConflictException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.repository.GroupRepo;
import aist.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

@Validator
public class GroupValidator {

    private final UserRepo userRepo;
    private final GroupRepo groupRepo;

    @Autowired
    public GroupValidator(UserRepo userRepo, GroupRepo groupRepo) {
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }

    public GroupDto forSave(GroupDto dto) {
        if (dto.getId() != null) {
            throw new AistBaseException("Группа для сохранения имеет id");
        }
        if (groupRepo.existsByName(dto.getName())) {
            throw new ConflictException("Группа с таким именем уже существует");
        }
        if (!userRepo.existsById(dto.getCreatedByUserId())) {
            throw new NotFoundException("Пользователь с таким id уже существует: " + dto.getCreatedByUserId());
        }
        return dto;
    }

}
