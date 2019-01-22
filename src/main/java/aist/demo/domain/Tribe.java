package aist.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "tribes")
public class Tribe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "tribe", fetch = FetchType.LAZY)
    private Set<TribeCommand> tribeCommands;

    @OneToMany(mappedBy = "tribe", fetch = FetchType.LAZY)
    private Set<User> users;

}
