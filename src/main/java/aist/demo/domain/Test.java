package aist.demo.domain;

import aist.demo.type.JsonbType;
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
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id")
    private AutomatedSystem system;

    private boolean availability;

    // TODO: 24.01.2019 разобраться, что это значит
    private String type;

    @Type(type = "JsonbType")
    private JsonElement jobTrigger;

    @ManyToMany(mappedBy = "tests", fetch = FetchType.LAZY)
    private Set<Tag> tags;

    private boolean isLegacy;

    // TODO: 24.01.2019 разобраться, не наследуется ли это от родительских чейнов
//    private Set<Group> groups;

}
