package aist.demo.hibernate.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class ContourDto {

    private Long id;
    @NonNull
    private String code;
    @NonNull
    private String name;
    private String description;
    private Set<Long> chainIdSet;

}
