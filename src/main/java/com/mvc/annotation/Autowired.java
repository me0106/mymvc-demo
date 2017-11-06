package com.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by 我啊 on 2017-10-27 11:55
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Autowired {
}
