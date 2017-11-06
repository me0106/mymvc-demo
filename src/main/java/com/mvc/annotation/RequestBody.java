package com.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by 我啊 on 2017-11-04 14:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface RequestBody {
}
