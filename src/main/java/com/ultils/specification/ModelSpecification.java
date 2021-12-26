package com.ultils.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ModelSpecification<T> implements Specification<T> {
    private final SearchCriteria criteria;

    public ModelSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getExpression().equalsIgnoreCase(">")) {
            return builder.greaterThan(
                    root.get(criteria.getColumn()), criteria.getKeySearch());
        } else if (criteria.getExpression().equalsIgnoreCase("<")) {
            return builder.lessThan(
                    root.get(criteria.getColumn()), criteria.getKeySearch());
        } else if (criteria.getExpression().equalsIgnoreCase("=")) {
            return builder.equal(
                    root.get(criteria.getColumn()), criteria.getKeySearch());
        } else if (criteria.getExpression().equalsIgnoreCase(">=")) {
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getColumn()), criteria.getKeySearch());
        } else if (criteria.getExpression().equalsIgnoreCase("<=")) {
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getColumn()), criteria.getKeySearch());
        } else if (criteria.getExpression().equalsIgnoreCase("CONTAINS")) {
            if (root.get(criteria.getColumn()).getJavaType() == String.class) {
                return builder.like(
                        root.get(criteria.getColumn()), "%" + criteria.getKeySearch() + "%");
            } else {
                return builder.equal(root.get(criteria.getColumn()), criteria.getKeySearch());
            }
        }
        return null;
    }
}
