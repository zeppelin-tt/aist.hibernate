package aist.demo.hibernate.domain.model;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class TribeCommandModel {

    private Long id;

    @NonNull
    private String name;

    private Long tribeId;

}
