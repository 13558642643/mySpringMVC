package com.ssm.zxw.annotation;

import java.lang.annotation.*;

/**
 * @author : ZXW
 * @version : 1.0
 * @date : 2021-01-22 10:01
 * @Description :
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZxwRequestParam {
    String value() default  "";
}
