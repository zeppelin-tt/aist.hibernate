package aist.demo.hibernate.layers.validator;

import aist.demo.hibernate.annotate.Validator;
import aist.demo.hibernate.domain.entity.TribeCommand;
import aist.demo.hibernate.domain.entity.User;
import aist.demo.hibernate.domain.model.TribeCommandModel;
import aist.demo.hibernate.domain.model.TribeModel;
import aist.demo.hibernate.exceptions.AistBaseException;
import aist.demo.hibernate.exceptions.ConflictException;
import aist.demo.hibernate.exceptions.ConsistentModelException;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.layers.converter.TribeCommandConverter;
import aist.demo.hibernate.layers.repository.TribeCommandRepo;
import aist.demo.hibernate.layers.repository.TribeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

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
