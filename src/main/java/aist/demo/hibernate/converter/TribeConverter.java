package aist.demo.hibernate.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.Tribe;
import aist.demo.hibernate.domain.TribeCommand;
import aist.demo.hibernate.domain.User;
import aist.demo.hibernate.dto.TribeDto;
import aist.demo.hibernate.repository.TribeCommandRepo;
import aist.demo.hibernate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class TribeConverter {

    private final UserRepo userRepo;
    private final TribeCommandRepo tribeCommandRepo;

    @Autowired
    public TribeConverter(UserRepo userRepo, TribeCommandRepo tribeCommandRepo) {
        this.userRepo = userRepo;
        this.tribeCommandRepo = tribeCommandRepo;
    }

    public Tribe convert(TribeDto model) {
        Tribe tribe = new Tribe(model.getName());
        tribe.setId(model.getId());
        Set<Long> userIdSet = model.getUserIdSet();
        Set<Long> tribeCommandIdSet = model.getTribeCommandIdSet();
        Set<User> users = null;
        if (model.getUserIdSet() != null) {
            users = new HashSet<>(userRepo.findAllById(userIdSet));
        }
        tribe.setUsers(users);
        Set<TribeCommand> commands = null;
        if (tribeCommandIdSet != null) {
            commands = new HashSet<>(tribeCommandRepo.findAllById(tribeCommandIdSet));
        }
        tribe.setTribeCommands(commands);
        return tribe;
    }

    // TODO: 19.01.2019 как-то криво получилось... рад любым советам и вмешательствам..)
    public TribeDto convert(Tribe tribe) {
        TribeDto model = new TribeDto(tribe.getName());
        Set<Long> userIdSet = tribe.getUsers() == null ?
                Collections.emptySet() :
                tribe.getUsers()
                        .stream()
                        .map(User::getId)
                        .collect(Collectors.toSet());
        model.setUserIdSet(userIdSet);
        Set<Long> tribeCommandIdSet = tribe.getTribeCommands() == null ?
                Collections.emptySet() :
                tribe.getTribeCommands()
                        .stream()
                        .map(TribeCommand::getId)
                        .collect(Collectors.toSet());
        model.setTribeCommandIdSet(tribeCommandIdSet);
        model.setId(tribe.getId());
        return model;
    }

}
