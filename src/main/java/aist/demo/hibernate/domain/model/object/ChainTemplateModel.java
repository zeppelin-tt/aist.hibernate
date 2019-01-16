package aist.demo.hibernate.domain.model.object;

import aist.demo.hibernate.domain.entry.Stand;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

@Data
@NoArgsConstructor
public class ChainTemplateModel {

    @NonNull
    private Long id;
    @NonNull
    private String name;
    private String form;
    @NonNull
    private Stand stanId;
    private String login;
    private Set<Long> groupIdSet;
    private boolean withoutForm;
    private String comment;

}
