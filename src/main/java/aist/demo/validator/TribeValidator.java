package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.domain.TribeCommand;
import aist.demo.domain.User;
import aist.demo.dto.TribeDto;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.ConsistentModelException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.exceptions.ConflictException;
import aist.demo.repository.TribeCommandRepo;
import aist.demo.repository.TribeRepo;
import aist.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Validator
public class TribeValidator {

    private final TribeRepo tribeRepo;
    private final CollectionValidator collectionValidator;

    @Autowired
    public TribeValidator(TribeRepo tribeRepo, CollectionValidator collectionValidator) {
        this.tribeRepo = tribeRepo;
        this.collectionValidator = collectionValidator;
    }

    public TribeDto forSave(TribeDto dto) {
        if (dto.getId() != null) {
            throw new AistBaseException("Трайб для сохранения имеет id");
        }
        if (tribeRepo.existsByName(dto.getName())) {
            throw new ConflictException("Трайб с таким именем уже существует");
        }
        collectionValidator.validateUsers(dto.getUserIdSet(), true, true);
        return dto;
    }

    public TribeDto forUpdate(TribeDto dto) {
        if (dto.getId() == null) {
            throw new AistBaseException("Трайб для сохранения не имеет id");
        }
        if (!tribeRepo.existsById(dto.getId())) {
            throw new NotFoundException("Id нет в базе: " + dto.getId());
        }
        collectionValidator.validateUsers(dto.getUserIdSet(), true, true);
        collectionValidator.validateTribeCommands(dto.getTribeCommandIdSet(), true, true);
        return dto;
    }

}
