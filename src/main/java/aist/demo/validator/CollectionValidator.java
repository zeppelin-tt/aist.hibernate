package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.domain.*;
import aist.demo.dto.TribeDto;
import aist.demo.exceptions.ConsistentModelException;
import aist.demo.repository.*;
import aist.demo.util.ValidateUtil;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Validator
public class CollectionValidator {

    private final GroupRepo groupRepo;
    private final AutomatedSystemRepo systemRepo;
    private final ChainRepo chainRepo;
    private final TribeCommandRepo commandRepo;
    private final UserRepo userRepo;

    @Autowired
    public CollectionValidator(GroupRepo groupRepo, AutomatedSystemRepo systemRepo, ChainRepo chainRepo, TribeCommandRepo commandRepo, UserRepo userRepo) {
        this.groupRepo = groupRepo;
        this.systemRepo = systemRepo;
        this.chainRepo = chainRepo;
        this.commandRepo = commandRepo;
        this.userRepo = userRepo;
    }

    void validateGroups(Set<Long> groupIdSet, boolean nullable, boolean emptyable) {
        checkNullAndEmpty(groupIdSet, nullable, emptyable, "Id автоматизированных систем");
        if (groupIdSet != null && !groupIdSet.isEmpty()) {
            Set<Group> dbGroups = new HashSet<>(groupRepo.findAllById(groupIdSet));
            if (dbGroups.size() != groupIdSet.size()) {
                Set<Long> idInDb = dbGroups
                        .stream()
                        .map(Group::getId)
                        .collect(Collectors.toSet());
                throw new ConsistentModelException("В БД нет следующих id Групп: " + Sets.difference(groupIdSet, idInDb));
            }
        }
    }

    void validateAutomatedSystems(Set<Long> systemIdSet, boolean nullable, boolean emptyable) {
        checkNullAndEmpty(systemIdSet, nullable, emptyable, "Id автоматизированных систем");
        Set<AutomatedSystem> dbAutomatedSystems = new HashSet<>(systemRepo.findAllById(systemIdSet));
        if (dbAutomatedSystems.size() != systemIdSet.size()) {
            Set<Long> idInDb = dbAutomatedSystems
                    .stream()
                    .map(AutomatedSystem::getId)
                    .collect(Collectors.toSet());
            throw new ConsistentModelException("В БД нет следующих id АС: " + Sets.difference(systemIdSet, idInDb));
        }
    }

    void validateChains(Set<Long> chainIdSet, boolean nullable, boolean emptyable) {
        checkNullAndEmpty(chainIdSet, nullable, emptyable, "Id генераторов");
        if (chainIdSet != null && !chainIdSet.isEmpty()) {
            Set<Chain> dbChains = new HashSet<>(chainRepo.findAllById(chainIdSet));
            if (dbChains.size() != chainIdSet.size()) {
                Set<Long> idInDb = dbChains
                        .stream()
                        .map(Chain::getId)
                        .collect(Collectors.toSet());
                throw new ConsistentModelException("Не все id цепочек контура есть в БД: " + Sets.difference(dbChains, idInDb));
            }
        }
    }

    void validateUsers(Set<Long> userIdSet, boolean nullable, boolean emptyable) {
        checkNullAndEmpty(userIdSet, nullable, emptyable, "Id команды трайба");
        if (userIdSet != null && !userIdSet.isEmpty()) {
            Set<User> dbUsers = new HashSet<>(userRepo.findAllById(userIdSet));
            if(dbUsers.size() != userIdSet.size()) {
                Set<Long> idInDb = dbUsers
                        .stream()
                        .map(User::getId)
                        .collect(Collectors.toSet());
                throw new ConsistentModelException("В БД нет следующих id пользователей: " + Sets.difference(dbUsers, idInDb));
            }
        }
    }

    void validateTribeCommands(Set<Long> tribeCommandIdSet, boolean nullable, boolean emptyable) {
        checkNullAndEmpty(tribeCommandIdSet, nullable, emptyable, "Id команды трайба");
        if (tribeCommandIdSet != null && !tribeCommandIdSet.isEmpty()) {
            Set<TribeCommand> dbTribeCommands = new HashSet<>(commandRepo.findAllById(tribeCommandIdSet));
            if(dbTribeCommands.size() != tribeCommandIdSet.size()) {
                Set<Long> idInDb = dbTribeCommands
                        .stream()
                        .map(TribeCommand::getId)
                        .collect(Collectors.toSet());
                throw new ConsistentModelException("В БД нет следующих id команд трайба: " + Sets.difference(tribeCommandIdSet, idInDb));
            }
        }
    }

    private void checkNullAndEmpty(Collection param, boolean nullable, boolean emptyable, String paramName) {
        if (!nullable) {
            ValidateUtil.instance.checkNull(param, paramName);
        }
        if (!emptyable) {
            ValidateUtil.instance.checkEmptyCollection(param, paramName);
        }
    }

}
