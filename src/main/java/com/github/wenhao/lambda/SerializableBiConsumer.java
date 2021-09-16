package com.github.wenhao.lambda;

import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.Function;

@FunctionalInterface
public interface SerializableBiConsumer<T, U> extends BiConsumer<T, U>, Serializable {
}
