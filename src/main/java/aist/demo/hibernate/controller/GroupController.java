package aist.demo.hibernate.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("groups")
public class GroupController {


// TODO: 20.01.2019 разобраться на работе.
//
//    /**
//     * Method gets record from "Groups" table. Records are all groups and users that are non members of each group
//     */
//    @GetMapping
//    public ResponseEntity getGroupsAndPotentialMembers(
////            @RequestHeader(required = false, value = TOKEN_PARAM_NAME) String sessionID
//    ) {
//            return GroupsDAO.getGroupsAndPotentialMembers(sessionID);
//    }
//
//    /**
//     * Method gets records from "Groups" table. Records are groups in which current user consists
//     */
//    @GetMapping("getGroups")
//    public ResponseEntity getGroups(@RequestHeader(required = false, value = TOKEN_PARAM_NAME) String sessionID) {
//        return ResponseEntity.ok(AuthUtils.getUserGroupsByToken(sessionID).stream()
//                .map(el -> Collections.singletonMap("name", el)).collect(Collectors.toList()));
//    }
//
//    /**
//     * Method creates record in "Groups" table
//     */
//    @PutMapping
//    public ResponseEntity createGroup(@RequestBody() String json,
//                                      @RequestHeader(required = false, value = TOKEN_PARAM_NAME) String sessionID) {
//        return GroupsDAO.setGroup(json, sessionID);
//    }
//
//    /**
//     * Method update record in "Groups" table
//     */
//    @PostMapping
//    public ResponseEntity updateGroup(@RequestBody() String json) {
//        return GroupsDAO.updateGroup(json);
//    }
//
//    /**
//     * Method delete record in "Groups" table
//     */
//    @DeleteMapping
//    public ResponseEntity deleteGroup(@RequestBody() String json) {
//        return GroupsDAO.deleteGroup(json);
//    }
}
