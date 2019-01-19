package aist.demo.hibernate.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
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
    private Set<Chain> chainSet;

}
