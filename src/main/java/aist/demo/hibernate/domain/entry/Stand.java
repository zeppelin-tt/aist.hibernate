package aist.demo.hibernate.domain.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Stand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String code;
    @NonNull
    private String name;
    private String description;

    @OneToMany(mappedBy = "chain", fetch = FetchType.LAZY) // cascade???
    private Set<Chain> chains;

}
