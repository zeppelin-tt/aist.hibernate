package aist.demo.domain;

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
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String login;

    private String password;

    private String token;

    @Email(message = "Email некорректен")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribe_id")
    private Tribe tribe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribe_command_id")
    private TribeCommand tribeCommand;

    @ManyToMany(mappedBy = "favoriteByUsers", fetch = FetchType.LAZY)
    private Set<Chain> favoriteChains;

    @OneToMany(mappedBy = "createdByUser", fetch = FetchType.LAZY)
    private Set<Group> createdGroups;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<Group> includedInGroups;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Chain> chains;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Test> tests;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Tag> tags;

    @OneToMany(mappedBy = "lockedBy", fetch = FetchType.LAZY)
    private Set<Order> ordersLocked;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Order> ordersCreated;

}
