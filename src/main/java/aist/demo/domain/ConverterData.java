package aist.demo.domain;

import aist.demo.type.JsonbType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "converter_data")
@TypeDefs({@TypeDef(name = "JsonbType", typeClass = JsonbType.class)})
public class ConverterData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // TODO: 24.01.2019 также не вижу пока смысла в many to many
    private Long systemId;

    private Long chainId;

    private String clientKey;

    private String clientValue;

    private String aistKey;

    private String aistValue;

    private String dataType;

}
