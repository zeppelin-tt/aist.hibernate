package aist.demo.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    @NonNull private String key;
    @NonNull private String operation;
    @NonNull private Object value;
    private boolean orPredicate = false;
}
