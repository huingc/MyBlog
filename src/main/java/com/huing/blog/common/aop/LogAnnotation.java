package com.huing.blog.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 * @Author huing
 * @Create 2022-07-12 14:54
 */
//TYPE代表可以放在类上面  method代表可以放在方法上
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     *     模块名称
     */
    String module() default "";

    /**
     *     操作名称
     */
    String operation() default "";
}
