package aist.demo.hibernate.domain.entry;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String password;

    private String token;

    @OneToMany(mappedBy = "createdByUser", fetch = FetchType.LAZY)
    private Set<Group> groups;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Chain> chains;

}
