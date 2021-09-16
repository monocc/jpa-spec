package com.github.wenhao.lambda;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class LambdaUtils {
    public static final String METHOD_NAME_GET = "get";
    public static final String METHOD_NAME_SET = "set";
    public static final String METHOD_NAME_IS = "is";

    public LambdaUtils() {
        throw new AssertionError("No LambdaUtils instance for you!");
    }

    /**
     * 缓存反序列化导致的问题
     */
    private static Map<Object, Field> cache = new ConcurrentHashMap<>();


    public static <T, R> Field getField(SerializableFunction<T, R> func) {
        return getFieldCache(func);
    }

    public static <T, R> List<Field> getFields(SerializableFunction<T, R>... funcs) {
        if (funcs != null && funcs.length != 0) {
            return Arrays.stream(funcs).map(LambdaUtils::getField).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    public static <T, U> Field getField(SerializableBiConsumer<T, U> biConsumer) {
        return getFieldCache(biConsumer);
    }

    public static <T, U> List<Field> getFields(SerializableBiConsumer<T, U>... biConsumers) {
        if (biConsumers != null && biConsumers.length != 0) {
            return Arrays.stream(biConsumers).map(LambdaUtils::getField).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private static <F> Field getFieldCache(F func) {
        return cache.computeIfAbsent(func, LambdaUtils::getField0);
    }

    @SneakyThrows
    private static <F> Field getField0(F func) {
        // 通过获取对象方法，判断是否存在该方法
        Method method = func.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(Boolean.TRUE);
        // 利用jdk的SerializedLambda 解析方法引用
        SerializedLambda serializedLambda = (SerializedLambda) method.invoke(func);
        String methodName = serializedLambda.getImplMethodName();
        String fieldName = resolveFieldName(methodName);

        // 第3步 获取的Class是字符串，并且包名是“/”分割，需要替换成“.”，才能获取到对应的Class对象
        String declaredClass = serializedLambda.getImplClass().replace("/", ".");
        final Class<?> aClass = ClassUtils.forName(declaredClass, null);
        // 第4步 Spring 中的反射工具类获取Class中定义的Field
        Field field = ReflectionUtils.findField(aClass, fieldName);
        if (field == null) {
            throw new NoSuchFieldException("Class [" + aClass + "] not found field name [" + fieldName + "]");
        }
        if (log.isInfoEnabled()) {
            log.info("# lambda: [{}], method:[{}], field: [{}]", func, methodName, field);
        }
        return field;
    }

    private static String resolveFieldName(String getOrSetMethodName) {
        String fieldName = "";
        if (getOrSetMethodName.startsWith(METHOD_NAME_GET)) {
            fieldName = getOrSetMethodName.substring(METHOD_NAME_GET.length());
        } else if (getOrSetMethodName.startsWith(METHOD_NAME_IS)) {
            fieldName = getOrSetMethodName.substring(METHOD_NAME_IS.length());
        } else if (getOrSetMethodName.startsWith(METHOD_NAME_SET)) {
            fieldName = getOrSetMethodName.substring(METHOD_NAME_SET.length());
        } else if (getOrSetMethodName.startsWith("lambda$")){
            throw new IllegalArgumentException("lambda expression is not support, getter or setter method reference is support");
        } else {
            throw new IllegalArgumentException(String.format("[%s] is not getter or setter method reference", getOrSetMethodName));
        }
        // 小写第一个字母
        if (!fieldName.isEmpty()) {
            fieldName = Introspector.decapitalize(fieldName);
        }
        return fieldName;
    }
}
