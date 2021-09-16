package com.github.wenhao.lambda;

import lombok.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 获取级联对象的属性
 * @author chd.y
 * @param <T>
 * @param <U>
 */
public class CascadeField<T, U> {
    private SerializableFunction<T, U> func;
    private SerializableBiConsumer<T, U> biConsumer;
    private CascadeField parent;


    public CascadeField(SerializableFunction<T, U> func) {
        this.func = func;
    }

    public CascadeField(SerializableBiConsumer<T, U> biConsumer) {
        this.biConsumer = biConsumer;
    }


    private CascadeField(CascadeField parent, @NonNull SerializableFunction<T, U> func) {
        this.parent = parent;
        this.func = func;
    }

    private CascadeField(CascadeField parent, @NonNull SerializableBiConsumer<T, U> biConsumer) {
        this.parent = parent;
        this.biConsumer = biConsumer;
    }

    public <V> CascadeField<T, V> with(SerializableFunction<U, V> func) {
        return new CascadeField(this, func);
    }

    public <V> CascadeField<T, V> with(SerializableBiConsumer<U, V> biConsumer) {
        return new CascadeField(this, biConsumer);
    }

    public List<CascadeField> getCascades() {
        List<CascadeField> list = new ArrayList<>(6);
        CascadeField tmp = this;
        while (tmp != null) {
            list.add(tmp);
            tmp = tmp.parent;
        }
        Collections.reverse(list);
        return list;
    }


    public List<Field> getFields() {
        List<Field> list = new ArrayList<>(this.getCascades().size());
        for (CascadeField c : this.getCascades()) {
            if (c.func != null) {
                list.add(LambdaUtils.getField(c.func));
            } else {
                list.add(LambdaUtils.getField(c.biConsumer));
            }
        }
        return list;
    }

    public List<String> getFieldNames() {
        List<String> list = new ArrayList<>(this.getCascades().size());
        for (CascadeField c : this.getCascades()) {
            if (c.func != null) {
                list.add(LambdaUtils.getField(c.func).getName());
            } else {
                list.add(LambdaUtils.getField(c.biConsumer).getName());
            }
        }
        return list;
    }

    public String getFieldName(String comma) {
        return String.join(comma, this.getFieldNames());
    }


    @Override
    public String toString() {
        return this.getFieldName(".");
    }

    public static <T, R> CascadeField<T, R> of(SerializableFunction<T, R> func) {
        return new CascadeField<>(func);
    }

    public static <T, U> CascadeField<T, U> of(SerializableBiConsumer<T, U> biConsumer) {
        return new CascadeField<>(biConsumer);
    }
}
