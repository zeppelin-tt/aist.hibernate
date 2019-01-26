package aist.demo.domain;

import aist.demo.dto.json.JobTrigger;
import aist.demo.type.JsonbType;

import lombok.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@TypeDefs({
        @TypeDef(name = "JsonbType", typeClass = JsonbType.class, parameters =
        @Parameter(name = JsonbType.CLASS, value = JobTrigger.CLASS))
})
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id")
    private AutomatedSystem system;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contour_id")
    private Contour contour;

    private boolean availability;

    // TODO: 24.01.2019 разобраться, что это значит
    private String type;

    @Type(type = "JsonbType")
    private JobTrigger jobTrigger;

    @ManyToMany(mappedBy = "tests", fetch = FetchType.LAZY)
    private Set<Tag> tags = new HashSet<>();

    private boolean isLegacy;

    private boolean needAccountPool;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tests_groups", joinColumns = @JoinColumn(name = "test_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

}
