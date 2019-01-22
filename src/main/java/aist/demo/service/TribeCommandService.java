package aist.demo.service;

import aist.demo.converter.TribeCommandConverter;
import aist.demo.dto.TribeCommandDto;
import aist.demo.exceptions.NotFoundException;
import aist.demo.domain.TribeCommand;
import aist.demo.repository.TribeCommandRepo;
import aist.demo.validator.TribeCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public TribeCommandDto save(String sessionId, TribeCommandDto commandDto) {
        commandValidator.forSave(commandDto);
        TribeCommand command = commandConverter.convert(commandDto);
        TribeCommand savedCommand = commandRepo.save(command);
        return commandConverter.convert(savedCommand);
    }

    @Transactional
    public Long update(String sessionId, TribeCommandDto commandDto) {
        commandValidator.forUpdate(commandDto);
        TribeCommand command = commandConverter.convert(commandDto);
        return commandRepo.save(command).getId();
    }

}
