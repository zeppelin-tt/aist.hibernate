package aist.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ChainDto {

    @NonNull
    private Long id;
    @NonNull
    private String name;
    private String form;
    @NonNull
    private Long contourId;
    private Set<Long> systems;
    private Long userId;
    private int[] testIdOrder;
    private Set<Long> groupIdSet;
    private boolean withoutForm;
    private Integer averageTimeSec;
    private Integer averageTimeByTestsSec;
    private boolean isLegacy;
    private String comment;

}
