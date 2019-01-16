package aist.demo.hibernate.domain.entry;

import aist.demo.hibernate.types.JsonbType;
import com.google.gson.JsonElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@TypeDefs({@TypeDef(name = "JsonbType", typeClass = JsonbType.class)})
public class Chain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @Type(type = "JsonbType")
    private JsonElement form;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_stand", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "stand_id"))
    private Set<Stand> stands;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_automated_system", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "automated_system_id"))
    private Set<AutomatedSystem> systems;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_tag", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_test", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "test_id"))
    private Set<Test> tests;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chain_group", joinColumns = @JoinColumn(name = "chain_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    private boolean withoutForm;

    private String comment;

}
