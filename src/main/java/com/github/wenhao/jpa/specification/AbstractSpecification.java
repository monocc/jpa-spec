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

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

abstract class AbstractSpecification<T> implements Specification<T>, Serializable {
    public static final String DELIMITER = ".";
    private final String[] properties;


    public AbstractSpecification(String property) {
        this(property, DELIMITER);
    }

    public AbstractSpecification(String[] properties) {
        this.properties = properties;
    }

    public AbstractSpecification(String property, String delimiter) {
        if (property.contains(delimiter)) {
            properties = StringUtils.split(property, delimiter);
        } else {
            properties = new String[] {property};
        }
    }

    public AbstractSpecification(Field field) {
        this.properties = new String[] {field.getName()};
    }


    public AbstractSpecification(List<Field> fields) {
        this.properties = fields.stream().map(Field::getName).toArray(String[]::new);
    }


    public String getProperty() {
        return properties[properties.length - 1];
    }

    /**
     * 取得最后的From
     * @param root
     * @return
     */
    public From getRoot(Root<T> root) {
        From from = root;
        for (int i = 0; i < properties.length - 1; i++) {
            String joinProperty = properties[i];
            from = from.join(joinProperty, JoinType.LEFT);
        }
        return from;
    }

    protected Object[] toObjects(Supplier<Object>... suppliers) {
        if (suppliers != null) {
            Object[] tmps = new Object[suppliers.length];
            for (int i = 0; i < suppliers.length; i++) {
                tmps[i] = suppliers[i].get();
            }
            return tmps;
        }
        return null;
    }

    protected Object toObjects(Supplier<Object> supplier) {
        return supplier != null ? supplier.get() : null;
    }

}
