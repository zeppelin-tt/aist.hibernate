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

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account_pool")
@TypeDefs({@TypeDef(name = "JsonbType", typeClass = JsonbType.class)})
public class AccountPool {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contour_id")
    private Contour contour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id")
    private AutomatedSystem system;

    // TODO: 24.01.2019 нужно создать отдельную entity Account. иначе будет костыль!
    @Type(type = "JsonbType")
    private JsonElement account;

    private LocalDateTime timeToFree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locked_by_order_id")
    private Order lockedBy;

    @Type(type = "JsonbType")
    private JsonElement filter;

}
