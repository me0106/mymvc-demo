package com.mvc.core.utils;

import java.lang.reflect.Parameter;

/**
 * Created by 我啊 on 2017-11-04 16:53
 */
public class ExtendsUtils {
    public static boolean isAssignFrom(Parameter parameter, Class clazz) {
        return parameter.getType().isAssignableFrom(clazz);
    }
}
