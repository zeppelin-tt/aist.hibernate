package aist.demo.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class TribeDto {

    private Long id;
    @NonNull
    private String name;
    private Set<Long> tribeCommandIdSet;
    private Set<Long> userIdSet;

}
