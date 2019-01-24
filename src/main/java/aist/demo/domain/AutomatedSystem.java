package aist.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "automated_systems")
// TODO: 20.01.2019 по-хорошему бы реализовать контроллер...
public class AutomatedSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String code;

    @NonNull
    private String description;

    private String name;

    @ManyToMany(mappedBy = "systems")
    private Set<Chain> chains;

    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    private Set<AccountPool> accountPoolSet;

}
