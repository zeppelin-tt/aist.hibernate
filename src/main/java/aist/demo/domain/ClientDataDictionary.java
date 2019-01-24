package aist.demo.domain;

import aist.demo.type.JsonbType;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "client_data_dictionary")
@TypeDefs({@TypeDef(name = "JsonbType", typeClass = JsonbType.class)})
public class ClientDataDictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // TODO: 24.01.2019 не вижу пока смысла реализовывать здесь связь many to many... хотя, может и стоит...
    private Long chainId;

    @Type(type = "JsonbType")
    private JsonElement data;

    private boolean isEnable;

    @Type(type = "JsonbType")
    private JsonElement defaultValues;

    @OneToMany(mappedBy = "clientData", fetch = FetchType.LAZY)
    private Set<PtdIntegration> ptdIntegrationOrders;

}
