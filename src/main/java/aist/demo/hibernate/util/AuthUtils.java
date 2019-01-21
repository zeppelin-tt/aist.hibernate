package aist.demo.hibernate.util;

import aist.demo.hibernate.domain.User;
import aist.demo.hibernate.exceptions.UnauthorizedException;
import aist.demo.hibernate.repository.UserRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
public class AuthUtils {
//    public static final String ANONYMOUS_USER = "anonymous";
//    public static final String ANONYMOUS_GROUP = "public";
//    public static final String TOKEN_PARAM_NAME = "SessionID";
//    public static final String ANONYMOUS_GROUP_JSON_ARR = "[\"" + ANONYMOUS_GROUP + "\"]";
//    public static final List<String> ANONYMOUS_GROUP_LIST = Collections.singletonList(ANONYMOUS_GROUP);

//    private final UserRepo userRepo;

//    @Autowired
//    public AuthUtils(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    /**
//     * Авторизация (проверка прав) пользователя на модификацию указанной сущности
//     * Модификация доступна только владельцу сущности
//     *
//     * @param table Тип сущности
//     * @param recId Идентификатор сущности
//     * @param token Идентифкатор пользователя
//     */
//    public void authorize(OWNED_TABLES table, String recId, String token) throws AistBaseException {
//        User user = getUserByToken(token, false);
//        String ownerFieldName = table.getOwnerField();
//
//        String owner = new QueryBuilder(table.getName(), ownerFieldName)
//                .appendEqualityFilter(table.getIdField(), recId).fetch()
//                .stream().findAny().orElseThrow(NotFoundException::new).get(ownerFieldName);
//
//        if (!StringUtils.equalsAny(owner, userName, ANONYMOUS_USER))
//            throw new ForbiddenException();
//    }
//
//    /**
//     * Авторизация (проверка прав) пользователя на чтение указанной сущности
//     * Чтение сущности доступна в следующих случаях:
//     * 1) Пользователь - владелец сущности
//     * 2) Пользователь входит в группу доступа
//     * 3) Владелец сущности - anonymous
//     * 4) Группа доступа - public
//     *
//     * @param table    Тип сущности
//     * @param recId    Идентификатор сущности
//     * @param username Имя пользователя
//     */
//    public static void authorizeRead(OWNED_TABLES table, String recId, String username) throws ForbiddenException, NotFoundException {
//        Set<String> groups = getUserGroups(username);
//
//        List<Map<String, String>> entityInfo = new QueryBuilder(table.getName(), table.getOwnerField(), table.getGroupField())
//                .appendEqualityFilter(table.getIdField(), recId).fetch();
//        if (entityInfo.isEmpty()) {
//            throw new NotFoundException();
//        }
//        String owner = entityInfo.stream().map(map -> map.get(table.getOwnerField()))
//                .findAny().orElseThrow(ForbiddenException::new);
//
//        String authorizedGroupsString = entityInfo.stream().map(map -> map.get(table.getGroupField()))
//                .findAny().orElseThrow(ForbiddenException::new);
//        Set<String> authorizedGroups;
//        try {
//            authorizedGroups = new ObjectMapper().readValue(authorizedGroupsString, new TypeReference<Set<String>>() {
//            });
//        } catch (IOException ignored) {
//            authorizedGroups = Collections.emptySet();
//        }
//
//        if (owner.equals(username))
//            return;
//
//        groups.retainAll(authorizedGroups);
//        if (groups.isEmpty()) {
//            throw new ForbiddenException();
//        }
//    }
//
//    // TODO: 20.01.2019 не нравится мне эта перегрузка... посмотреть повнимательнее
//    public User getUserByToken(String token) throws UnauthorizedException {
//        return getUserByToken(token, true);
//    }
//
//    public User getUserByToken(String token, boolean isAnonymousAllowed) throws UnauthorizedException {
//        if (StringUtils.isEmpty(token)) {
//            if (isAnonymousAllowed) {
//                return null;
//            } else {
//                throw new UnauthorizedException();
//            }
//        }
//        Set<User> users = userRepo.findByToken(token);
//        if (users.size() != 1) {
//            throw new UnauthorizedException(token);
//        }
//        return users.iterator().next();
//    }
//
////    public static Set<String> getUserGroupsByToken(String token) {
////        return getUserGroups(getUsernameByToken(token));
////    }
//
////    public static Set<String> getUserGroups(String username) {
////        Set<String> groups = new HashSet<>();
////        groups.add(ANONYMOUS_GROUP);
////        try {
////            groups.addAll(db.fetchAll(String.format(
////                    "select %s from %s where %s ~ '\\y%s\\y'",
////                    GROUPS.GROUP_NAME, GROUPS.TABLE_NAME, GROUPS.MEMBERS, username
////            )).stream().map(map -> map.get(GROUPS.GROUP_NAME)).collect(toSet()));
////        } catch (SQLException ignored) {
////        }
////        return groups;
////    }

}
