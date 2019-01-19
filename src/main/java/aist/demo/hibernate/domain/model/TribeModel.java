package aist.demo.hibernate.domain.model;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class TribeModel {

    private Long id;
    @NonNull
    private String name;
    private Set<Long> tribeCommandIdSet;
    private Set<Long> userIdSet;

}
