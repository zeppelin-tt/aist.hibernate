package aist.demo.dto;

import aist.demo.dto.json.JobTrigger;
import lombok.Data;

import java.util.Set;

@Data
public class TestDto {

    private Long id;
    private String name;
    private Long creatorId;
    private Long systemId;
    private Long contourId;
    private boolean availability;
    private String type; //????
    private JobTrigger jobTrigger;
    private Set<String> tags;
    private boolean isLegacy;
    private boolean needAccountPool;
    private Set<Long> groups;

}
