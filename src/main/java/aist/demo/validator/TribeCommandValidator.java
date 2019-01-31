package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.dto.TribeCommandDto;
import aist.demo.exceptions.ConflictException;
import aist.demo.repository.TribeCommandRepo;
import aist.demo.repository.TribeRepo;
import aist.demo.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Validator
public class TribeCommandValidator {

    private final TribeRepo tribeRepo;
    private final TribeCommandRepo commandRepo;

    @Autowired
    public TribeCommandValidator(TribeRepo tribeRepo, TribeCommandRepo commandRepo) {
        this.tribeRepo = tribeRepo;
        this.commandRepo = commandRepo;
    }

    public TribeCommandDto forSave(TribeCommandDto dto) {
        ValidateUtil.instance.checkNull(dto.getId(), "Id команды трайба");
        if (commandRepo.existsByName(dto.getName())) {
            throw new ConflictException("Команда трайба с таким именем уже существует");
        }
        if (dto.getTribeId() == null) {
            throw new NotFoundException("У команды трайба не указан id трайба");
        }
        if (!tribeRepo.existsById(dto.getTribeId())) {
            throw new NotFoundException("Трайба с таким ID не существует: " + dto.getTribeId());
        }
        return dto;
    }

    public TribeCommandDto forUpdate(TribeCommandDto dto) {
        ValidateUtil.instance.checkNonNull(dto.getId(), "Id команды трайба");
        if (!tribeRepo.existsById(dto.getId())) {
            throw new NotFoundException("Id нет в базе: " + dto.getId());
        }
        return dto;
    }

}
