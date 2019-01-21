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

    public TribeCommand convert(TribeCommandDto dto) {
        TribeCommand command = new TribeCommand(dto.getName());
        command.setId(dto.getId());
        command.setTribe(tribeRepo.getOne(dto.getTribeId()));
        return command;
    }

    public TribeCommandDto convert(TribeCommand command) {
        TribeCommandDto dto = new TribeCommandDto(command.getName());
        dto.setId(command.getId());
        dto.setTribeId(command.getTribe().getId());
        return dto;
    }

    public Set<TribeCommandDto> convertSet(Set<TribeCommand> commands) {
        return commands
                .stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
