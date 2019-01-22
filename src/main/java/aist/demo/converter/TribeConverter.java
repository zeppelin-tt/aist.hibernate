package aist.demo.converter;

import aist.demo.annotate.Converter;
import aist.demo.domain.Tribe;
import aist.demo.dto.TribeDto;
import aist.demo.domain.TribeCommand;
import aist.demo.domain.User;
import aist.demo.repository.TribeCommandRepo;
import aist.demo.repository.UserRepo;
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

    public Tribe convert(TribeDto dto) {
        Tribe tribe = new Tribe(dto.getName());
        tribe.setId(dto.getId());
        Set<Long> userIdSet = dto.getUserIdSet();
        Set<Long> tribeCommandIdSet = dto.getTribeCommandIdSet();
        Set<User> users = dto.getUserIdSet() == null ?
                Collections.emptySet() :
                new HashSet<>(userRepo.findAllById(userIdSet));
        tribe.setUsers(users);
        Set<TribeCommand> commands = tribeCommandIdSet == null ?
                Collections.emptySet() :
                new HashSet<>(tribeCommandRepo.findAllById(tribeCommandIdSet));
        tribe.setTribeCommands(commands);
        return tribe;
    }

    // TODO: 19.01.2019 как-то криво получилось... рад любым советам и вмешательствам..)
    public TribeDto convert(Tribe tribe) {
        TribeDto dto = new TribeDto(tribe.getName());
        Set<Long> userIdSet = tribe.getUsers() == null ?
                Collections.emptySet() :
                tribe.getUsers()
                        .stream()
                        .map(User::getId)
                        .collect(Collectors.toSet());
        dto.setUserIdSet(userIdSet);
        Set<Long> tribeCommandIdSet = tribe.getTribeCommands() == null ?
                Collections.emptySet() :
                tribe.getTribeCommands()
                        .stream()
                        .map(TribeCommand::getId)
                        .collect(Collectors.toSet());
        dto.setTribeCommandIdSet(tribeCommandIdSet);
        dto.setId(tribe.getId());
        return dto;
    }

}
