package aist.demo.hibernate.validator;

import aist.demo.hibernate.annotate.Validator;
import aist.demo.hibernate.model.TribeCommandModel;
import aist.demo.hibernate.exceptions.AistBaseException;
import aist.demo.hibernate.exceptions.ConflictException;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.converter.TribeCommandConverter;
import aist.demo.hibernate.repository.TribeCommandRepo;
import aist.demo.hibernate.repository.TribeRepo;
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

    public TribeCommandModel forSave(TribeCommandModel model) {
        if (model.getId() != null) {
            throw new AistBaseException("Команда трайба для сохранения имеет id");
        }
        if (commandRepo.existsByName(model.getName())) {
            throw new ConflictException("Команда трайба с таким именем уже существует");
        }
        if (model.getTribeId() == null) {
            throw new NotFoundException("У команды трайба не указан id трайба");
        }
        if (!tribeRepo.existsById(model.getTribeId())) {
            throw new NotFoundException("Трайба с таким ID не существует: " + model.getTribeId());
        }
        return model;
    }

    public TribeCommandModel forUpdate(TribeCommandModel model) {
        if (model.getId() == null) {
            throw new AistBaseException("Команда трайба для редактирования не имеет id");
        }
        if (!tribeRepo.existsById(model.getId())) {
            throw new NotFoundException("Id нет в базе: " + model.getId());
        }
        return model;
    }

}
