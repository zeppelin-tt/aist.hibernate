package aist.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GroupDto {

    private Long id;
    private String name;
    private Long createdByUserId;
    private Set<Long> chainIdSet;

}
