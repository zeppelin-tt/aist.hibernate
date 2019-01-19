package aist.demo.hibernate.domain.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserModel {

    private Long id;
    private String login;
    private String password;
    private Integer token;
    private String email;
    private Long tribeId;
    private Long commandTribeId;
    private Set<Long> groups;
    private Set<Long> chains;

}
