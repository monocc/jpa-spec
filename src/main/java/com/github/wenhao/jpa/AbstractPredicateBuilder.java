package com.github.wenhao.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static javax.persistence.criteria.Predicate.BooleanOperator.OR;

/**
 * 抽象 Predicate Builder
 * @author chd.y
 */
public class AbstractPredicateBuilder<T> {
    private final Predicate.BooleanOperator operator;
    private List<Specification<T>> specifications;

    public AbstractPredicateBuilder(Predicate.BooleanOperator operator) {
        this.operator = operator;
        this.specifications = new ArrayList<>();
    }

    public AbstractPredicateBuilder(AbstractPredicateBuilder builder) {
        this.operator = builder.operator;
        this.specifications = builder.specifications;
    }

    public <R extends AbstractPredicateBuilder<T>> R predicate(Specification specification) {
        return predicate(true, specification);
    }

    public <R extends AbstractPredicateBuilder<T>> R predicate(boolean condition, Specification specification) {
        if (condition) {
            this.specifications.add(specification);
        }
        return (R) this;
    }

    public <R extends AbstractPredicateBuilder<T>> R predicate(boolean condition, Supplier<Specification> supplier) {
        if (condition) {
            this.specifications.add(supplier.get());
        }
        return (R) this;
    }

    public Specification<T> build() {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate[] predicates = new Predicate[specifications.size()];
            for (int i = 0; i < specifications.size(); i++) {
                predicates[i] = specifications.get(i).toPredicate(root, query, cb);
            }
            if (Objects.equals(predicates.length, 0)) {
                return null;
            }
            return OR.equals(operator) ? cb.or(predicates) : cb.and(predicates);
        };
    }
}
