package aist.demo.hibernate.service;

import aist.demo.hibernate.domain.TribeCommand;
import aist.demo.hibernate.dto.TribeCommandDto;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.converter.TribeCommandConverter;
import aist.demo.hibernate.repository.TribeCommandRepo;
import aist.demo.hibernate.validator.TribeCommandValidator;
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

    public TribeCommandDto find(Long id) {
        TribeCommand command = commandRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Contour by id: " + id));
        return commandConverter.convert(command);
    }

    public Set<TribeCommandDto> findByTribeId(Long tribeId) {
        Set<TribeCommand> commands = commandRepo.findByTribeId(tribeId);
        return commandConverter.convertSet(commands);
    }

    public Set<TribeCommandDto> findAll() {
        return commandRepo.findAll()
                .stream()
                .map(commandConverter::convert)
                .collect(Collectors.toSet());
    }

    public TribeCommandDto save(String sessionId, TribeCommandDto commandDto) {
        commandValidator.forSave(commandDto);
        TribeCommand command = commandConverter.convert(commandDto);
        TribeCommand savedCommand = commandRepo.save(command);
        return commandConverter.convert(savedCommand);
    }

    public Long update(String sessionId, TribeCommandDto commandDto) {
        commandValidator.forUpdate(commandDto);
        TribeCommand command = commandConverter.convert(commandDto);
        return commandRepo.save(command).getId();
    }

}
