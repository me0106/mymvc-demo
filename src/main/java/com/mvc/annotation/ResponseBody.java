package com.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by 我啊 on 2017-11-04 13:37
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResponseBody {
}
