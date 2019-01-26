package aist.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "contours")
public class Contour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String code;

    @NonNull
    private String name;

    private String description;

    @OneToMany(mappedBy = "contour", fetch = FetchType.LAZY) // cascade???
    private Set<Test> tests;

    @OneToMany(mappedBy = "contour", fetch = FetchType.LAZY) // cascade???
    private Set<Chain> chains;

    @OneToMany(mappedBy = "contour", fetch = FetchType.LAZY)
    private Set<AccountPool> accountPoolSet = new HashSet<>();

}
