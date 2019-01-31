package aist.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ChainDto {

    private Long id;
    private String name;
    private String form;
    private Long contourId;
    private Set<Long> systemIdSet;
    private Long creatorId;
    private Integer[] testIdOrder;
    private Set<Long> groupIdSet;
    private boolean withoutForm;
    private Integer averageTimeSec;
    private Integer averageTimeByTestsSec;
    private boolean isLegacy;
    private String comment;

}
