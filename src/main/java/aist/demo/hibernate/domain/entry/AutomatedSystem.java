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
