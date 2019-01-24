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
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@TypeDefs({@TypeDef(name = "JsonbType", typeClass = JsonbType.class)})
// TODO: 24.01.2019 это был main...
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    // TODO: 24.01.2019 нужен ли он вообще?... Есть такое чувство, что с ним только одни проблемы будут.
//    private String uniqNumber;

    private LocalDateTime creationTime;

    // TODO: 24.01.2019 переделать в таблицу енумов
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locked_by_user_id")
    private User lockedBy;

    private boolean isLocked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chain_id")
    private Chain chain;

    // TODO: 24.01.2019 нужен ли контур? по идее, его получаем из Чейна...

    // TODO: 24.01.2019 имеет ли смысл реализовывать one to one ? вот что-то сомневаюсь))
    @Type(type = "JsonbType")
    private JsonElement data;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private PtdIntegration ptdIntegration;

    @OneToMany(mappedBy = "lockedBy", fetch = FetchType.LAZY)
    private Set<AccountPool> accountsLocked;

}
