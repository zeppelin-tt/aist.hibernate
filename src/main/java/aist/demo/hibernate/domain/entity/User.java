package aist.demo.hibernate.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String password;

    private Integer token;

    @Email(message = "Email некорректен")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribe_id")
    private Tribe tribe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "command_tribe_id")
    private TribeCommand tribeCommand;

    @OneToMany(mappedBy = "createdByUser", fetch = FetchType.LAZY)
    private Set<Group> groups;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Chain> chains;

}
