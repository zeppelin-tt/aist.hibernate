package aist.demo.hibernate.validator;

import aist.demo.hibernate.annotate.Validator;
import aist.demo.hibernate.model.TribeModel;
import aist.demo.hibernate.exceptions.AistBaseException;
import aist.demo.hibernate.exceptions.ConflictException;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.repository.TribeCommandRepo;
import aist.demo.hibernate.repository.TribeRepo;
import aist.demo.hibernate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

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

    public TribeModel forSave(TribeModel model) {
        if (model.getId() != null) {
            throw new AistBaseException("Трайб для сохранения имеет id");
        }
        if (tribeRepo.existsByName(model.getName())) {
            throw new ConflictException("Трайб с таким именем уже существует");
        }
//        validateUsers(model);
        return model;
    }

    public TribeModel forUpdate(TribeModel model) {
        if (model.getId() == null) {
            throw new AistBaseException("Трайб для сохранения не имеет id");
        }
        if (!tribeRepo.existsById(model.getId())) {
            throw new NotFoundException("Id нет в базе: " + model.getId());
        }
//        validateUsers(model);
//        validateTribeCommands(model);
        return model;
    }

//    // TODO: 19.01.2019 дубли... может дженериками?
//    private void validateUsers(TribeModel model) {
//        Set<Long> userIdSet = model.getUserIdSet();
//        if (userIdSet != null && !userIdSet.isEmpty()) {
//            Set<User> dbUsers = new HashSet<>(userRepo.findAllById(userIdSet));
//            if(dbUsers.size() != userIdSet.size()) {
//                throw new ConsistentModelException("Не все id пользователей есть в БД");
//            }
//        }
//    }

//    // TODO: 19.01.2019 кажется, это лишнее... мы, окажывается, не можем апдейтить из Ентитти другие значения в других энтити. Собственно, имеет смысл))))Тогда и передавать их с фронта не имеет смысла...
//    private void validateTribeCommands(TribeModel model) {
//        Set<Long> tribeCommandIdSet = model.getTribeCommandIdSet();
//        if (tribeCommandIdSet != null && !tribeCommandIdSet.isEmpty()) {
//            Set<TribeCommand> dbTribeCommands = new HashSet<>(commandRepo.findAllById(tribeCommandIdSet));
//            if(dbTribeCommands.size() != tribeCommandIdSet.size()) {
//                Set<Long> idInDb = dbTribeCommands
//                        .stream()
//                        .map(TribeCommand::getId)
//                        .collect(Collectors.toSet());
//                tribeCommandIdSet.removeAll(idInDb);
//                throw new ConsistentModelException("В БД нет следующих id команд трайба: " + tribeCommandIdSet.toString());
//            }
//        }
//    }

}
