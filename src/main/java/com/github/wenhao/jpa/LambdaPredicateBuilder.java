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

import com.github.wenhao.jpa.specification.BetweenSpecification;
import com.github.wenhao.jpa.specification.EqualSpecification;
import com.github.wenhao.jpa.specification.GeSpecification;
import com.github.wenhao.jpa.specification.GtSpecification;
import com.github.wenhao.jpa.specification.InSpecification;
import com.github.wenhao.jpa.specification.LeSpecification;
import com.github.wenhao.jpa.specification.LikeSpecification;
import com.github.wenhao.jpa.specification.LtSpecification;
import com.github.wenhao.jpa.specification.NotEqualSpecification;
import com.github.wenhao.jpa.specification.NotInSpecification;
import com.github.wenhao.jpa.specification.NotLikeSpecification;

import com.github.wenhao.lambda.CascadeField;
import com.github.wenhao.lambda.LambdaUtils;
import com.github.wenhao.lambda.SerializableBiConsumer;
import com.github.wenhao.lambda.SerializableFunction;

import java.util.Arrays;
import java.util.Collection;

public class LambdaPredicateBuilder<T> extends AbstractPredicateBuilder<T> {

    public LambdaPredicateBuilder(AbstractPredicateBuilder builder) {
        super(builder);
    }

    // -------------------------------
    // getter method
    // -------------------------------
    public <R> LambdaPredicateBuilder<T> eq(SerializableFunction<T, R> func, Object... values) {
        return eq(true, func, values);
    }

    public <R> LambdaPredicateBuilder<T> eq(boolean condition, SerializableFunction<T, R> func, Object... values) {
        return this.predicate(condition, new EqualSpecification<T>(LambdaUtils.getField(func), values));
    }

    public <R> LambdaPredicateBuilder<T> ne(SerializableFunction<T, R> func, Object... values) {
        return ne(true, func, values);
    }

    public <R> LambdaPredicateBuilder<T> ne(boolean condition, SerializableFunction<T, R> getter, Object... values) {
        return this.predicate(condition, new NotEqualSpecification<T>(LambdaUtils.getField(getter), values));
    }

    public <R> LambdaPredicateBuilder<T> gt(SerializableFunction<T, R> getter, Comparable<?> compare) {
        return gt(true, getter, compare);
    }

    public <R> LambdaPredicateBuilder<T> gt(boolean condition, SerializableFunction<T, R> getter, Comparable<?> compare) {
        return this.predicate(condition, new GtSpecification<T>(LambdaUtils.getField(getter), compare));
    }

    public <R> LambdaPredicateBuilder<T> ge(SerializableFunction<T, R> getter, Comparable<?> compare) {
        return ge(true, getter, compare);
    }

    public <R> LambdaPredicateBuilder<T> ge(boolean condition, SerializableFunction<T, R> getter, Comparable<? extends Object> compare) {
        return this.predicate(condition, new GeSpecification<T>(LambdaUtils.getField(getter), compare));
    }

    public <R> LambdaPredicateBuilder<T> lt(SerializableFunction<T, R> getter, Comparable<?> number) {
        return lt(true, getter, number);
    }

    public <R> LambdaPredicateBuilder<T> lt(boolean condition, SerializableFunction<T, R> getter, Comparable<?> compare) {
        return this.predicate(condition, new LtSpecification<T>(LambdaUtils.getField(getter), compare));
    }

    public <R> LambdaPredicateBuilder<T> le(SerializableFunction<T, R> getter, Comparable<?> compare) {
        return le(true, getter, compare);
    }

    public <R> LambdaPredicateBuilder<T> le(boolean condition, SerializableFunction<T, R> getter, Comparable<?> compare) {
        return this.predicate(condition, new LeSpecification<T>(LambdaUtils.getField(getter), compare));
    }

    public <R> LambdaPredicateBuilder<T> between(SerializableFunction<T, R> getter, Object lower, Object upper) {
        return between(true, getter, lower, upper);
    }

    public <R> LambdaPredicateBuilder<T> between(boolean condition, SerializableFunction<T, R> getter, Object lower, Object upper) {
        return this.predicate(condition, new BetweenSpecification<T>(LambdaUtils.getField(getter), lower, upper));
    }

    public <R> LambdaPredicateBuilder<T> like(SerializableFunction<T, R> getter, String... patterns) {
        return like(true, getter, patterns);
    }

    public <R> LambdaPredicateBuilder<T> like(boolean condition, SerializableFunction<T, R> getter, String... patterns) {
        return this.predicate(condition, new LikeSpecification<T>(LambdaUtils.getField(getter), patterns));
    }

    public <R> LambdaPredicateBuilder<T> notLike(SerializableFunction<T, R> getter, String... patterns) {
        return notLike(true, getter, patterns);
    }

    public <R> LambdaPredicateBuilder<T> notLike(boolean condition, SerializableFunction<T, R> getter, String... patterns) {
        return this.predicate(condition, new NotLikeSpecification<T>(LambdaUtils.getField(getter), patterns));
    }

    public <R> LambdaPredicateBuilder<T> in(SerializableFunction<T, R> getter, Collection<?> values) {
        return this.in(true, getter, values);
    }

    public <R> LambdaPredicateBuilder<T> in(boolean condition, SerializableFunction<T, R> getter, Collection<?> values) {
        return this.predicate(condition, new InSpecification<T>(LambdaUtils.getField(getter), values));
    }

    public <R> LambdaPredicateBuilder<T> notIn(SerializableFunction<T, R> getter, Collection<?> values) {
        return this.notIn(true, getter, values);
    }

    public <R> LambdaPredicateBuilder<T> notIn(boolean condition, SerializableFunction<T, R> getter, Collection<?> values) {
        return this.predicate(condition, new NotInSpecification<T>(LambdaUtils.getField(getter), values));
    }

    // -------------------------------
    // setter method
    // -------------------------------
    public <U> LambdaPredicateBuilder<T> eq(SerializableBiConsumer<T, U> setterFunc, Object... values) {
        return eq(true, setterFunc, values);
    }

    public <U> LambdaPredicateBuilder<T> eq(boolean condition, SerializableBiConsumer<T, U> setterFunc, Object... values) {
        return this.predicate(condition, new EqualSpecification<T>(LambdaUtils.getField(setterFunc), values));
    }

    public <U> LambdaPredicateBuilder<T> ne(SerializableBiConsumer<T, U> setterFunc, Object... values) {
        return ne(true, setterFunc, values);
    }

    public <U> LambdaPredicateBuilder<T> ne(boolean condition, SerializableBiConsumer<T, U> setterFunc, Object... values) {
        return this.predicate(condition, new NotEqualSpecification<T>(LambdaUtils.getField(setterFunc), values));
    }

    public <U> LambdaPredicateBuilder<T> gt(SerializableBiConsumer<T, U> setterFunc, Comparable<?> compare) {
        return gt(true, setterFunc, compare);
    }

    public <U> LambdaPredicateBuilder<T> gt(boolean condition, SerializableBiConsumer<T, U> setterFunc, Comparable<?> compare) {
        return this.predicate(condition, new GtSpecification<T>(LambdaUtils.getField(setterFunc), compare));
    }

    public <U> LambdaPredicateBuilder<T> ge(SerializableBiConsumer<T, U> setterFunc, Comparable<?> compare) {
        return ge(true, setterFunc, compare);
    }

    public <U> LambdaPredicateBuilder<T> ge(boolean condition, SerializableBiConsumer<T, U> setterFunc, Comparable<? extends Object> compare) {
        return this.predicate(condition, new GeSpecification<T>(LambdaUtils.getField(setterFunc), compare));
    }

    public <U> LambdaPredicateBuilder<T> lt(SerializableBiConsumer<T, U> setterFunc, Comparable<?> number) {
        return lt(true, setterFunc, number);
    }

    public <U> LambdaPredicateBuilder<T> lt(boolean condition, SerializableBiConsumer<T, U> setterFunc, Comparable<?> compare) {
        return this.predicate(condition, new LtSpecification<T>(LambdaUtils.getField(setterFunc), compare));
    }

    public <U> LambdaPredicateBuilder<T> le(SerializableBiConsumer<T, U> setterFunc, Comparable<?> compare) {
        return le(true, setterFunc, compare);
    }

    public <U> LambdaPredicateBuilder<T> le(boolean condition, SerializableBiConsumer<T, U> setterFunc, Comparable<?> compare) {
        return this.predicate(condition, new LeSpecification<T>(LambdaUtils.getField(setterFunc), compare));
    }

    public <U> LambdaPredicateBuilder<T> between(SerializableBiConsumer<T, U> setterFunc, Object lower, Object upper) {
        return between(true, setterFunc, lower, upper);
    }

    public <U> LambdaPredicateBuilder<T> between(boolean condition, SerializableBiConsumer<T, U> setterFunc, Object lower, Object upper) {
        return this.predicate(condition, new BetweenSpecification<T>(LambdaUtils.getField(setterFunc), lower, upper));
    }

    public <U> LambdaPredicateBuilder<T> like(SerializableBiConsumer<T, U> setterFunc, String... patterns) {
        return like(true, setterFunc, patterns);
    }

    public <U> LambdaPredicateBuilder<T> like(boolean condition, SerializableBiConsumer<T, U> setterFunc, String... patterns) {
        return this.predicate(condition, new LikeSpecification<T>(LambdaUtils.getField(setterFunc), patterns));
    }

    public <U> LambdaPredicateBuilder<T> notLike(SerializableBiConsumer<T, U> setterFunc, String... patterns) {
        return notLike(true, setterFunc, patterns);
    }

    public <U> LambdaPredicateBuilder<T> notLike(boolean condition, SerializableBiConsumer<T, U> setterFunc, String... patterns) {
        return this.predicate(condition, new NotLikeSpecification<T>(LambdaUtils.getField(setterFunc), patterns));
    }

    public <U> LambdaPredicateBuilder<T> in(SerializableBiConsumer<T, U> setterFunc, Collection<?> values) {
        return this.in(true, setterFunc, values);
    }

    public <U> LambdaPredicateBuilder<T> in(boolean condition, SerializableBiConsumer<T, U> setterFunc, Collection<?> values) {
        return this.predicate(condition, new InSpecification<T>(LambdaUtils.getField(setterFunc), values));
    }

    public <U> LambdaPredicateBuilder<T> notIn(SerializableBiConsumer<T, U> setterFunc, Collection<?> values) {
        return this.notIn(true, setterFunc, values);
    }

    public <U> LambdaPredicateBuilder<T> notIn(boolean condition, SerializableBiConsumer<T, U> setterFunc, Collection<?> values) {
        return this.predicate(condition, new NotInSpecification<T>(LambdaUtils.getField(setterFunc), values));
    }


    // -------------------------------
    // cascade
    // -------------------------------
    public <V> LambdaPredicateBuilder<T> eq(CascadeField<T, V> cascade, Object... values) {
        return eq(true, cascade, values);
    }

    public <V> LambdaPredicateBuilder<T> eq(boolean condition, CascadeField<T, V> cascade, Object... values) {
        return this.predicate(condition, new EqualSpecification<T>(cascade.getFields(), values));
    }

    public <V> LambdaPredicateBuilder<T> ne(CascadeField<T, V> cascade, Object... values) {
        return ne(true, cascade, values);
    }

    public <V> LambdaPredicateBuilder<T> ne(boolean condition, CascadeField<T, V> cascade, Object... values) {
        return this.predicate(condition, new NotEqualSpecification<T>(cascade.getFields(), values));
    }

    public <V> LambdaPredicateBuilder<T> gt(CascadeField<T, V> cascade, Comparable<?> compare) {
        return gt(true, cascade, compare);
    }

    public <V> LambdaPredicateBuilder<T> gt(boolean condition, CascadeField<T, V> cascade, Comparable<?> compare) {
        return this.predicate(condition, new GtSpecification<T>(cascade.getFields(), compare));
    }

    public <V> LambdaPredicateBuilder<T> ge(CascadeField<T, V> cascade, Comparable<?> compare) {
        return ge(true, cascade, compare);
    }

    public <V> LambdaPredicateBuilder<T> ge(boolean condition, CascadeField<T, V> cascade, Comparable<? extends Object> compare) {
        return this.predicate(condition, new GeSpecification<T>(cascade.getFields(), compare));
    }

    public <V> LambdaPredicateBuilder<T> lt(CascadeField<T, V> cascade, Comparable<?> number) {
        return lt(true, cascade, number);
    }

    public <V> LambdaPredicateBuilder<T> lt(boolean condition, CascadeField<T, V> cascade, Comparable<?> compare) {
        return this.predicate(condition, new LtSpecification<T>(cascade.getFields(), compare));
    }

    public <V> LambdaPredicateBuilder<T> le(CascadeField<T, V> cascade, Comparable<?> compare) {
        return le(true, cascade, compare);
    }

    public <V> LambdaPredicateBuilder<T> le(boolean condition, CascadeField<T, V> cascade, Comparable<?> compare) {
        return this.predicate(condition, new LeSpecification<T>(cascade.getFields(), compare));
    }

    public <V> LambdaPredicateBuilder<T> between(CascadeField<T, V> cascade, Object lower, Object upper) {
        return between(true, cascade, lower, upper);
    }

    public <V> LambdaPredicateBuilder<T> between(boolean condition, CascadeField<T, V> cascade, Object lower, Object upper) {
        return this.predicate(condition, new BetweenSpecification<T>(cascade.getFields(), lower, upper));
    }

    public <V> LambdaPredicateBuilder<T> like(CascadeField<T, V> cascade, String... patterns) {
        return like(true, cascade, patterns);
    }

    public <V> LambdaPredicateBuilder<T> like(boolean condition, CascadeField<T, V> cascade, String... patterns) {
        return this.predicate(condition, new LikeSpecification<T>(cascade.getFields(), patterns));
    }

    public <V> LambdaPredicateBuilder<T> notLike(CascadeField<T, V> cascade, String... patterns) {
        return notLike(true, cascade, patterns);
    }

    public <V> LambdaPredicateBuilder<T> notLike(boolean condition, CascadeField<T, V> cascade, String... patterns) {
        return this.predicate(condition, new NotLikeSpecification<T>(cascade.getFields(), patterns));
    }

    public <V> LambdaPredicateBuilder<T> in(CascadeField<T, V> cascade, Collection<?> values) {
        return this.in(true, cascade, values);
    }

    public <V> LambdaPredicateBuilder<T> in(boolean condition, CascadeField<T, V> cascade, Collection<?> values) {
        return this.predicate(condition, new InSpecification<T>(cascade.getFields(), values));
    }

    public <V> LambdaPredicateBuilder<T> notIn(CascadeField<T, V> cascade, Collection<?> values) {
        return this.notIn(true, cascade, values);
    }

    public <V> LambdaPredicateBuilder<T> notIn(boolean condition, CascadeField<T, V> cascade, Collection<?> values) {
        return this.predicate(condition, new NotInSpecification<T>(cascade.getFields(), values));
    }
}
