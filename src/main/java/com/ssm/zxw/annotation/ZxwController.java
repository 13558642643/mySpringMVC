package com.ssm.zxw.annotation;

import java.lang.annotation.*;

/**
 * @author : ZXW
 * @version : 1.0
 * @date : 2021-01-22 9:47
 * @Description :
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) //表示在运行时可以反射获取
@Documented
public @interface ZxwController {
    String value() default "";
}
