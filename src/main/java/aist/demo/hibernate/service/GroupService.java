package aist.demo.hibernate.service;

import aist.demo.hibernate.domain.Group;
import aist.demo.hibernate.domain.User;
import aist.demo.hibernate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class GroupService {

    private final UserRepo userRepo;

    @Autowired
    public GroupService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    // TODO: 20.01.2019 тут без реальной базы не разобраться, так что до понедельника...
//    public ResponseEntity getGroupsAndPotentialMembers(Integer sessionID) throws UnauthorizedException {
//        List<Map<String, String>> allGroupsAndMembers;
//        List<Map<String, String>> allUsers;
//
//        User user = userRepo.findOneByToken(sessionID);
//        Set<Group> groups = user.getGroups();
//
//
//        String result;
//        allGroupsAndMembers = new QueryBuilder("groups", "name, members").appendEqualityFilter("owner", owner).fetch(db);
//        allUsers = new QueryBuilder("owners", "login").fetch(db);
//        if (allGroupsAndMembers.isEmpty()) {
//            result = buildJSONFromGroupQuery(Collections.emptyList(), Collections.emptyList()).toString();
//        } else {
//            if (allUsers.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            result = buildJSONFromGroupQuery(allGroupsAndMembers, allUsers).toString();
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


}
