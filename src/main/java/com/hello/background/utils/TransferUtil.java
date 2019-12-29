package com.hello.background.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 转换工具
 *
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@Slf4j
public class TransferUtil {
    /**
     * 转换
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void transfer(Object source, Object target) {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();
        Method[] targetMethods = target.getClass().getDeclaredMethods();
        Method[] sourceMethods = source.getClass().getDeclaredMethods();
        // 遍历目标属性与源属性，进行赋值
        for (Field tf : targetFields) {
            for (Field sf : sourceFields) {
                if (tf.getName().equals(sf.getName()) && tf.getType().getName().equals(sf.getType().getName())) {
                    // 目标属性和源属性的名称与类型都相同的情况下
                    try {
                        String setMethodName = "set" + tf.getName().substring(0, 1).toUpperCase() + tf.getName().substring(1);
                        String getMethodName = "get" + tf.getName().substring(0, 1).toUpperCase() + tf.getName().substring(1);
                        for (Method sm : sourceMethods) {
                            if (sm.getName().equals(getMethodName)) {
                                Object value = sm.invoke(source);
                                for (Method tm : targetMethods) {
                                    if (tm.getName().equals(setMethodName)) {
                                        tm.invoke(target, value);
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        log.error("{}", ex);
                    }
                }
            }
        }
    }

    /**
     * 泛型类转换
     *
     * @param source 源对象
     * @param t      目标类
     * @param <T>    目标类
     * @return 目标类实例
     */
    public static <T> Object transferTo(Object source, T t) {
        Object o = null;
        try {
            o = t.getClass().newInstance();
            transfer(source, o);
        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return o;
    }
}
