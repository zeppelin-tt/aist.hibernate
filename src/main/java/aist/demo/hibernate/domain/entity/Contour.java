package aist.demo.hibernate.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Contour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String code;

    @NonNull
    private String name;

    private String description;

    @OneToMany(mappedBy = "contour", fetch = FetchType.LAZY) // cascade???
    private Set<Chain> chains;

}
