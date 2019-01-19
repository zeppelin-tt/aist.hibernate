package aist.demo.hibernate.domain.model;

import aist.demo.hibernate.domain.entity.Chain;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<Chain> chains;

}
