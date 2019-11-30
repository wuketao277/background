package com.hello.background.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

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
        // 遍历目标属性与源属性，进行赋值
        for (Field tf : targetFields) {
            for (Field sf : sourceFields) {
                if (tf.getName().equals(sf.getName()) && tf.getType().getName().equals(sf.getType().getName())) {
                    // 目标属性和源属性的名称与类型都相同的情况下
                    try {
                        tf.set(target, sf);
                    } catch (Exception ex) {
                        log.error("{}", ex);
                    }
                }
            }
        }
    }
}
