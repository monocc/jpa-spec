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
package com.github.wenhao.jpa;

import com.github.wenhao.lambda.LambdaUtils;
import com.github.wenhao.lambda.SerializableFunction;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * 排序工具类
 * @author wenhao
 * @author chd.y
 */
public final class Sorts {

    private Sorts() {
    }

    public static Builder builder() {
        return new Builder();
    }


    /**
     * 基类Builder
     */
    @Getter
    public static class AbstractBuilder {
        private List<Order> orders;

        public AbstractBuilder(List<Order> orders) {
            this.orders = orders;
        }

        public Sort build() {
            return Sort.by(orders);
        }
    }

    public static final class Builder extends AbstractBuilder {
        public Builder() {
            super(new ArrayList<>());
        }

        public Builder asc(String property) {
            return asc(true, property);
        }

        public Builder desc(String property) {
            return desc(true, property);
        }

        public Builder asc(boolean condition, String property) {
            if (condition) {
                super.getOrders().add(new Order(ASC, property));
            }
            return this;
        }

        public Builder desc(boolean condition, String property) {
            if (condition) {
                super.getOrders().add(new Order(DESC, property));
            }
            return this;
        }

        public LambdaBuilder lambda() {
            return new LambdaBuilder(this);
        }
    }


    public static final class LambdaBuilder extends AbstractBuilder {

        public LambdaBuilder(AbstractBuilder builder) {
           super(builder.orders);
        }

        public <T, R> LambdaBuilder asc(SerializableFunction<T, R> getterFunc) {
            return asc(true, getterFunc);
        }

        public <T, R> LambdaBuilder desc(SerializableFunction<T, R> getterFunc) {
            return desc(true, getterFunc);
        }

        public <T, R> LambdaBuilder asc(boolean condition, SerializableFunction<T, R> getterFunc) {
            if (condition) {
                super.getOrders().add(new Order(ASC, LambdaUtils.getField(getterFunc).getName()));
            }
            return this;
        }

        public <T, R> LambdaBuilder desc(boolean condition, SerializableFunction<T, R> getterFunc) {
            if (condition) {
                super.getOrders().add(new Order(DESC, LambdaUtils.getField(getterFunc).getName()));
            }
            return this;
        }

    }
}
