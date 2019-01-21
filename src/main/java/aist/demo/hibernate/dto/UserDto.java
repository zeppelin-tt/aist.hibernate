package aist.demo.hibernate.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String login;
    private String password;
    private String token;
    private String email;
    private Long tribeId;
    private Long commandTribeId;
    private Set<Long> groups;
    private Set<Long> chains;

}
