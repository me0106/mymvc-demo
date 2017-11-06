package com.mvc.annotation;

import com.mvc.enums.HttpMethod;

import java.lang.annotation.*;

/**
 * Created by 我啊 on 2017-10-27 13:21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestMapping {
    String value();
    HttpMethod[] method() default  {};
}
