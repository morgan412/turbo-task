package com.turbo.schedule.model;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义调度方法注解
 *
 * @author zouxq
 * @date 2019/10/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Task {

    /**
     * 定时任务描述
     */
    @AliasFor("desc")
    String value() default "";

    /**
     * 定时任务描述
     */
    @AliasFor("value")
    String desc() default "";

    /**
     * 执行时间描述，如：每1分钟1次；每天凌晨1点
     */
    String time() default "";
}
