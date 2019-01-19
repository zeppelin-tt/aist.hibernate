package aist.demo.hibernate.layers.service;

import aist.demo.hibernate.domain.entity.TribeCommand;
import aist.demo.hibernate.domain.model.TribeCommandModel;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.layers.converter.TribeCommandConverter;
import aist.demo.hibernate.layers.repository.TribeCommandRepo;
import aist.demo.hibernate.layers.validator.TribeCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TribeCommandService {

    private final TribeCommandRepo commandRepo;
    private final TribeCommandConverter commandConverter;
    private final TribeCommandValidator commandValidator;

    @Autowired
    public TribeCommandService(TribeCommandRepo commandRepo, TribeCommandConverter commandConverter, TribeCommandValidator commandValidator) {
        this.commandRepo = commandRepo;
        this.commandConverter = commandConverter;
        this.commandValidator = commandValidator;
    }

    public TribeCommandModel find(Long id) {
        TribeCommand command = commandRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Contour by id: " + id));
        return commandConverter.convert(command);
    }

    public Set<TribeCommandModel> findByTribeId(Long tribeId) {
        Set<TribeCommand> commands = commandRepo.findByTribeId(tribeId);
        return commandConverter.convertSet(commands);
    }

    public Set<TribeCommandModel> findAll() {
        return commandRepo.findAll()
                .stream()
                .map(commandConverter::convert)
                .collect(Collectors.toSet());
    }

    public TribeCommandModel save(String sessionId, TribeCommandModel commandModel) {
        commandValidator.forSave(commandModel);
        TribeCommand command = commandConverter.convert(commandModel);
        TribeCommand savedCommand = commandRepo.save(command);
        return commandConverter.convert(savedCommand);
    }

    public Long update(String sessionId, TribeCommandModel commandModel) {
        commandValidator.forUpdate(commandModel);
        TribeCommand command = commandConverter.convert(commandModel);
        return commandRepo.save(command).getId();
    }

}
