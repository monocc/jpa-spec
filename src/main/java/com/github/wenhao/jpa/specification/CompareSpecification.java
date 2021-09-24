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

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

public abstract class CompareSpecification<T> extends AbstractSpecification<T> {
    private final transient Comparable<Object> compare;

    public CompareSpecification(String property, Comparable<? extends Object> compare) {
        super(property);
        this.compare = (Comparable<Object>) compare;
    }

    public CompareSpecification(Field field, Comparable<? extends Object> compare) {
        super(field);
        this.compare = (Comparable<Object>) compare;
    }

    public CompareSpecification(List<Field> fields, Comparable<? extends Object> compare) {
        super(fields);
        this.compare = (Comparable<Object>) compare;
    }

    public CompareSpecification(String property, Supplier<Comparable<? extends Object>> compareSupplier) {
        super(property);
        this.compare = (Comparable<Object>) compareSupplier.get();
    }

    public CompareSpecification(Field field, Supplier<Comparable<? extends Object>> compareSupplier) {
        super(field);
        this.compare = (Comparable<Object>) compareSupplier.get();
    }

    public CompareSpecification(List<Field> fields, Supplier<Comparable<? extends Object>> compareSupplier) {
        super(fields);
        this.compare = (Comparable<Object>) compareSupplier.get();
    }


    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = getRoot(root);
        String field = getProperty();
        return doToPredicate(from, cb, field, compare);
    }


    /**
     * 获取单个predicate
     */
    protected abstract Predicate doToPredicate(From root, CriteriaBuilder cb, String field,  Comparable<Object> compare);
}
