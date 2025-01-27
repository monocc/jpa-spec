/**
 * Copyright © 2019, Wen Hao <wenhao@126.com>.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.wenhao.jpa.specification;


import com.github.wenhao.lambda.CascadeField;
import com.github.wenhao.lambda.LambdaUtils;
import com.github.wenhao.lambda.SerializableFunction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;


public class BetweenSpecification<T> extends AbstractSpecification<T> {
    private final transient Comparable<Object> lower;
    private final transient Comparable<Object> upper;

    public BetweenSpecification(String property, Object lower, Object upper) {
        super(property);
        this.lower = (Comparable<Object>) lower;
        this.upper = (Comparable<Object>) upper;
    }

    public BetweenSpecification(Field field, Object lower, Object upper) {
        super(field);
        this.lower = (Comparable<Object>) lower;
        this.upper = (Comparable<Object>) upper;
    }

    public BetweenSpecification(List<Field> fields, Object lower, Object upper) {
        super(fields);
        this.lower = (Comparable<Object>) lower;
        this.upper = (Comparable<Object>) upper;
    }

    public BetweenSpecification(String property, Supplier<Object> lowerSupplier, Supplier<Object> upperSupplier) {
        super(property);
        this.lower = (Comparable<Object>) lowerSupplier.get();
        this.upper = (Comparable<Object>) upperSupplier.get();
    }

    public BetweenSpecification(Field field, Supplier<Object> lowerSupplier, Supplier<Object> upperSupplier) {
        super(field);
        this.lower = (Comparable<Object>) lowerSupplier.get();
        this.upper = (Comparable<Object>) upperSupplier.get();
    }

    public BetweenSpecification(List<Field> fields, Supplier<Object> lowerSupplier, Supplier<Object> upperSupplier) {
        super(fields);
        this.lower = (Comparable<Object>) lowerSupplier.get();
        this.upper = (Comparable<Object>) upperSupplier.get();
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = getRoot(root);
        String field = getProperty();
        return cb.between(from.get(field), lower, upper);
    }
}
