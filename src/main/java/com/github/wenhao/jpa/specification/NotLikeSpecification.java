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

import lombok.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

public class NotLikeSpecification<T> extends LikeSpecification<T> {


    public NotLikeSpecification(String property, @NonNull String... patterns) {
        super(property, patterns);
    }

    public NotLikeSpecification(Field field, @NonNull String... patterns) {
        super(field, patterns);
    }

    public NotLikeSpecification(List<Field> fields, @NonNull String... patterns) {
        super(fields, patterns);
    }

    public NotLikeSpecification(String property, @NonNull Supplier<String>... suppliers) {
        super(property, suppliers);
    }

    public NotLikeSpecification(Field field, @NonNull Supplier<String>... suppliers) {
        super(field, suppliers);
    }

    public NotLikeSpecification(List<Field> fields, @NonNull Supplier<String>... suppliers) {
        super(fields, suppliers);
    }

    @Override
    protected Predicate doToPredicate(From root, CriteriaBuilder cb, Object value, String field) {
        return super.doToPredicate(root, cb, value, field).not();
    }
}
