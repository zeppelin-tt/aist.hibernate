package aist.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "param_category_dictionary")
public class ParamCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String code;

    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<ParamSubCategory> subCategories;

}
