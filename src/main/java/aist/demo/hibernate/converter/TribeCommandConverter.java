package aist.demo.hibernate.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.TribeCommand;
import aist.demo.hibernate.dto.TribeCommandDto;
import aist.demo.hibernate.repository.TribeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class TribeCommandConverter {

    private final TribeRepo tribeRepo;

    @Autowired
    public TribeCommandConverter(TribeRepo tribeRepo) {
        this.tribeRepo = tribeRepo;
    }

    public TribeCommand convert(TribeCommandDto model) {
        TribeCommand command = new TribeCommand(model.getName());
        command.setId(model.getId());
        command.setTribe(tribeRepo.getOne(model.getTribeId()));
        return command;
    }

    public TribeCommandDto convert(TribeCommand command) {
        TribeCommandDto model = new TribeCommandDto(command.getName());
        model.setId(command.getId());
        model.setTribeId(command.getTribe().getId());
        return model;
    }

    public Set<TribeCommandDto> convertSet(Set<TribeCommand> commands) {
        return commands
                .stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
