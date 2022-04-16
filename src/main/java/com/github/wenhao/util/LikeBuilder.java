package com.github.wenhao.util;

import java.util.ArrayList;
import java.util.List;

public class LikeBuilder {

    private List<String> values = new ArrayList<>();

    public static LikeBuilder builder() {
        return new LikeBuilder();
    }

    public List<String> build() {
        return values;
    }

    public LikeBuilder endsWith(String value) {
        values.add("%" + value);
        return this;
    }

    public LikeBuilder startsWith(String value) {
        values.add(value + "%");
        return this;
    }

    public LikeBuilder contains(String value) {
        values.add("%" + value + "%");
        return this;
    }


}
