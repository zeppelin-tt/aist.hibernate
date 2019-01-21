package aist.demo.hibernate.dto;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class TribeCommandDto {

    private Long id;

    @NonNull
    private String name;

    private Long tribeId;

}
