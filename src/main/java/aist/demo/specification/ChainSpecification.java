package aist.demo.specification;

import aist.demo.domain.Chain;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class ChainSpecification implements Specification<Chain> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Chain> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case ":": {
                return builder.equal(root
                        .get(criteria.getKey()), criteria.getValue());
            }
            case "!": {
                return builder.notEqual(root
                        .get(criteria.getKey()), criteria.getValue());
            }
            case ">": {
                return builder.greaterThan(root
                        .get(criteria.getKey()), criteria.getValue().toString());
            }
            case "<": {
                return builder.lessThanOrEqualTo(root
                        .get(criteria.getKey()), criteria.getValue().toString());
            }
            case "~": {
                return builder.like(root.<String>
                        get(criteria.getKey()), criteria.getValue().toString());
            }
            case "startsWith": {
                return builder.like(root.<String>
                        get(criteria.getKey()), criteria.getValue() + "%");
            }
            case "endWith": {
                return builder.like(root.<String>
                        get(criteria.getKey()), "%" + criteria.getValue());
            }
            case "contains": {
                return builder.like(root.<String>
                        get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }
            default: {
                return null;
            }
        }
    }

}
