package aist.demo.hibernate.domain.model.object;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class StandModel {

    private Long id;
    @NonNull
    private String code;
    @NonNull
    private String name;
    private String description;
    private Set<Long> chainIdSet;

}
