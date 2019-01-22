package aist.demo.domain;

import aist.demo.type.IntArrayType;
import aist.demo.type.JsonbType;
import com.google.gson.JsonElement;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@TypeDefs({@TypeDef(name = "JsonbType", typeClass = JsonbType.class), @TypeDef(name = "IntArrayType", typeClass = IntArrayType.class)})
@Table(name = "chains")
public class Chain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Type(type = "JsonbType")
    private JsonElement form;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contour_id")
    private Contour contour;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_automated_system", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "automated_system_id"))
    private Set<AutomatedSystem> systems;

    @Type(type = "IntArrayType")
    private int[] testIdOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_group", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    private boolean withoutForm;

    private Integer averageTimeSec;

    private Integer averageTimeByTestsSec;

    private boolean isLegacy;

    @Length(max = 1024, message = "Превышена максимальная длина комментария")
    @Column(length = 1024)
    private String comment;
}