package aist.demo.hibernate.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.TribeCommand;
import aist.demo.hibernate.model.TribeCommandModel;
import aist.demo.hibernate.repository.TribeCommandRepo;
import aist.demo.hibernate.repository.TribeRepo;
import aist.demo.hibernate.repository.UserRepo;
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

    public TribeCommand convert(TribeCommandModel model) {
        TribeCommand command = new TribeCommand(model.getName());
        command.setId(model.getId());
        command.setTribe(tribeRepo.getOne(model.getTribeId()));
        return command;
    }

    public TribeCommandModel convert(TribeCommand command) {
        TribeCommandModel model = new TribeCommandModel(command.getName());
        model.setId(command.getId());
        model.setTribeId(command.getTribe().getId());
        return model;
    }

    public Set<TribeCommandModel> convertSet(Set<TribeCommand> commands) {
        return commands
                .stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
