package aist.demo.hibernate.layers.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.entity.*;
import aist.demo.hibernate.domain.model.TribeModel;
import aist.demo.hibernate.layers.repository.TribeCommandRepo;
import aist.demo.hibernate.layers.repository.UserRepo;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
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

    public Tribe convert(TribeModel model) {
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
        tribe.setTribeCommandSet(commands);
        return tribe;
    }

    // TODO: 19.01.2019 как-то криво получилось... рад любым советам и вмешательствам..)
    public TribeModel convert(Tribe tribe) {
        TribeModel model = new TribeModel(tribe.getName());
        if (tribe.getUsers() == null) {
            model.setUserIdSet(Sets.newHashSet());
        } else {
            Set<Long> userIdSet = tribe.getUsers()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
            model.setUserIdSet(userIdSet);
        }
        if (tribe.getTribeCommandSet() == null) {
            model.setTribeCommandIdSet(Sets.newHashSet());
        } else {
            Set<Long> tribeCommandIdSet = tribe.getTribeCommandSet()
                    .stream()
                    .map(TribeCommand::getId)
                    .collect(Collectors.toSet());
            model.setTribeCommandIdSet(tribeCommandIdSet);
        }
        model.setId(tribe.getId());
        return model;
    }

}
