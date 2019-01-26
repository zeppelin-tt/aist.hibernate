package aist.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TagDto {

    private Long id;
    private String name;
    private String value;
    private Long creatorId;
    private Set<Long> tests;

}
