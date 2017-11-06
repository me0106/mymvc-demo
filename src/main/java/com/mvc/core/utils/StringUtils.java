package com.mvc.core.utils;

/**
 * Created by 我啊 on 2017-11-06 16:17
 */
public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }
}
