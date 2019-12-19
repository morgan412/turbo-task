package com.turbo.schedule.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Spring 容器工具类
 *
 * @author zouxq
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtil.context = context;
    }

    /**
     * 获取Spring bean容器
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 根据实例名获取bean
     *
     * @param name 实例名
     * @return bean
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }


    /**
     * 根据类型获取bean
     *
     * @param type 类类型
     * @return bean
     */
    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    /**
     * 根据类型获取beans
     *
     * @param type 类类型
     * @return beans
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return context.getBeansOfType(type);
    }

    /**
     * 根据注解获取beans
     *
     * @param annotationType 注解
     * @return beans
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return context.getBeansWithAnnotation(annotationType);
    }
}
