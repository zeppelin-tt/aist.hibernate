package aist.demo.hibernate.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GroupDto {

    private Long id;
    private String name;
    private Set<Long> chainIdSet;

}
