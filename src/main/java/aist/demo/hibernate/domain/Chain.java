package aist.demo.hibernate.domain;

import aist.demo.hibernate.type.JsonbType;
import com.google.gson.JsonElement;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@TypeDefs({@TypeDef(name = "JsonbType", typeClass = JsonbType.class)})
@Table(name = "chains")
public class Chain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @Type(type = "JsonbType")
    private JsonElement form;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stand_id")
    private Contour contour;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_automated_system", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "automated_system_id"))
    private Set<AutomatedSystem> systems;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "chain_test", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "test_id"))
//    private Set<Test> tests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_group", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    private boolean withoutForm;

    private Integer averageTimeSec;

    private Integer averageTimeByTestsSec;

    private String comment;

}
