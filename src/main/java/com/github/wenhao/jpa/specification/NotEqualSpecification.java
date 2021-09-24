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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

public class NotEqualSpecification<T> extends ValuesSpecification<T> {

    public NotEqualSpecification(String property, Object... values) {
        super(property, values);
    }

    public NotEqualSpecification(Field field, Object... values) {
        super(field, values);
    }

    public NotEqualSpecification(List<Field> fields, Object... values) {
        super(fields, values);
    }

    public NotEqualSpecification(String property, Supplier<Object>... suppliers) {
        super(property, suppliers);
    }

    public NotEqualSpecification(Field field, Supplier<Object>... suppliers) {
        super(field, suppliers);
    }

    public NotEqualSpecification(List<Field> fields, Supplier<Object>... suppliers) {
        super(fields, suppliers);
    }


    @Override
    protected Predicate doToPredicate(From root, CriteriaBuilder cb, Object value, String field) {
        return value == null ? cb.isNotNull(root.get(field)) : cb.notEqual(root.get(field), value);
    }
}

