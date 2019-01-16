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
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @JoinColumn(name = "user_id")
    private User createdByUser;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<Chain> chains;

}
