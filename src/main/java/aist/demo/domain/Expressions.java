package aist.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "expressions_dictionary")
public class Expressions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: 24.01.2019 также непонятно, следует ли связывать? Не вижу смысла.
    private Long paramCategoryId;

    private Long paramSubcategoryId;

    private String expression;

    private String description;

}
