package aist.demo.hibernate.dto;

import aist.demo.hibernate.domain.Contour;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    private Contour stanId;
    private String login;
    private Set<Long> groupIdSet;
    private boolean withoutForm;
    private String comment;

}