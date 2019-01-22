package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.domain.TribeCommand;
import aist.demo.domain.User;
import aist.demo.dto.TribeDto;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.ConsistentModelException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.exceptions.ConflictException;
import aist.demo.repository.TribeCommandRepo;
import aist.demo.repository.TribeRepo;
import aist.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Validator
public class TribeValidator {

    private final TribeRepo tribeRepo;
    private final UserRepo userRepo;
    private final TribeCommandRepo commandRepo;

    @Autowired
    public TribeValidator(TribeRepo tribeRepo, UserRepo userRepo, TribeCommandRepo commandRepo) {
        this.tribeRepo = tribeRepo;
        this.userRepo = userRepo;
        this.commandRepo = commandRepo;
    }

    public TribeDto forSave(TribeDto dto) {
        if (dto.getId() != null) {
            throw new AistBaseException("Трайб для сохранения имеет id");
        }
        if (tribeRepo.existsByName(dto.getName())) {
            throw new ConflictException("Трайб с таким именем уже существует");
        }
        validateUsers(dto);
        return dto;
    }

    public TribeDto forUpdate(TribeDto dto) {
        if (dto.getId() == null) {
            throw new AistBaseException("Трайб для сохранения не имеет id");
        }
        if (!tribeRepo.existsById(dto.getId())) {
            throw new NotFoundException("Id нет в базе: " + dto.getId());
        }
        validateUsers(dto);
        validateTribeCommands(dto);
        return dto;
    }

    // TODO: 19.01.2019 дубли... может дженериками?
    private void validateUsers(TribeDto dto) {
        Set<Long> userIdSet = dto.getUserIdSet();
        if (userIdSet != null && !userIdSet.isEmpty()) {
            Set<User> dbUsers = new HashSet<>(userRepo.findAllById(userIdSet));
            if(dbUsers.size() != userIdSet.size()) {
                Set<Long> idInDb = dbUsers
                        .stream()
                        .map(User::getId)
                        .collect(Collectors.toSet());
                userIdSet.removeAll(idInDb);
                throw new ConsistentModelException("В БД нет следующих id пользователей: " + userIdSet.toString());
            }
        }
    }

//    // TODO: 19.01.2019 кажется, это лишнее... мы, окажывается, не можем апдейтить из Ентитти другие значения в других энтити. Собственно, имеет смысл))))Тогда и передавать их с фронта не имеет смысла...
    private void validateTribeCommands(TribeDto dto) {
        Set<Long> tribeCommandIdSet = dto.getTribeCommandIdSet();
        if (tribeCommandIdSet != null && !tribeCommandIdSet.isEmpty()) {
            Set<TribeCommand> dbTribeCommands = new HashSet<>(commandRepo.findAllById(tribeCommandIdSet));
            if(dbTribeCommands.size() != tribeCommandIdSet.size()) {
                Set<Long> idInDb = dbTribeCommands
                        .stream()
                        .map(TribeCommand::getId)
                        .collect(Collectors.toSet());
                tribeCommandIdSet.removeAll(idInDb);
                throw new ConsistentModelException("В БД нет следующих id команд трайба: " + tribeCommandIdSet.toString());
            }
        }
    }

}
