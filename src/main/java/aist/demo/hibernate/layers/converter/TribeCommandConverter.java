package aist.demo.hibernate.layers.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.entity.Tribe;
import aist.demo.hibernate.domain.entity.TribeCommand;
import aist.demo.hibernate.domain.entity.User;
import aist.demo.hibernate.domain.model.TribeCommandModel;
import aist.demo.hibernate.domain.model.TribeModel;
import aist.demo.hibernate.layers.repository.TribeCommandRepo;
import aist.demo.hibernate.layers.repository.TribeRepo;
import aist.demo.hibernate.layers.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
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
