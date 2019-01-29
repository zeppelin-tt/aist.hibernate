package aist.demo.specification;

import aist.demo.domain.Chain;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChainSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public ChainSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public ChainSpecificationsBuilder with(String key, String operation, Object value, boolean isOrPredicate) {
        params.add(new SearchCriteria(key, operation, value, isOrPredicate));
        return this;
    }

    public ChainSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value, false));
        return this;
    }

    public Specification<Chain> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(ChainSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate() ?
                    Specification.where(result).or(new ChainSpecification(params.get(i))) :
                    Specification.where(result).and(new ChainSpecification(params.get(i)));
        }
        return result;
    }

}
