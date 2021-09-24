package com.github.wenhao.jpa.specification;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

public abstract class ValuesSpecification<T> extends AbstractSpecification<T> {

    /**
     * 存储的数据
     */
    private final transient Object[] values;

    public ValuesSpecification(String property, Object... values) {
        super(property);
        this.values = values;
    }

    public ValuesSpecification(Field field, Object... values) {
        super(field);
        this.values = values;
    }

    public ValuesSpecification(List<Field> fields, Object... values) {
        super(fields);
        this.values = values;
    }

    public ValuesSpecification(String property, Supplier<Object>... suppliers) {
        super(property);
        this.values = super.toObjects(suppliers);
    }

    public ValuesSpecification(Field field, Supplier<Object>... suppliers) {
        super(field);
        this.values = super.toObjects(suppliers);
    }


    public ValuesSpecification(List<Field> fields, Supplier<Object>... suppliers) {
        super(fields);
        this.values = super.toObjects(suppliers);
    }


    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = getRoot(root);
        String field = getProperty();
        if (values == null) {
            return cb.isNull(from.get(field));
        }
        if (values.length == 1) {
            return doToPredicate(from, cb, values[0], field);
        }

        Predicate[] predicates = new Predicate[values.length];
        for (int i = 0; i < values.length; i++) {
            predicates[i] = doToPredicate(root, cb, values[i], field);
        }
        return cb.or(predicates);
    }


    /**
     * 获取单个predicate
     * @param root
     * @param cb
     * @param value
     * @param field
     * @return
     */
    protected abstract Predicate doToPredicate(From root, CriteriaBuilder cb, Object value, String field);
}
