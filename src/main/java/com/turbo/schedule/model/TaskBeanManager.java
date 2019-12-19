package com.turbo.schedule.model;

import com.turbo.schedule.util.CronUtil;
import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时任务信息 管理类
 *
 * @author zouxq
 */
@Data
public class TaskBeanManager {

    /**
     * 定时任务信息 容器
     * key 为 className.methodName
     */
    private static Map<String, TaskBean> taskBeanMap = new HashMap<>();

    /**
     * 设置任务类对象 实例集合
     *
     * @param taskMap 实例集合
     */
    public static void setTaskMap(Map<String, ITask> taskMap) {
        taskMap.forEach((instanceName, task) -> {
            // 遍历方法
            Method[] methods = task.getClass().getDeclaredMethods();
            String className = instanceName.substring(0, 1).toUpperCase() + instanceName.substring(1);
            for (Method method : methods) {

                // 获取注解
                // 由于目标方法被AOP代理，要使用AnnotationUtils.findAnnotation方法获取注解
                Scheduled scheduled = AnnotationUtils.findAnnotation(method, Scheduled.class);
                Task taskAnnotation = AnnotationUtils.findAnnotation(method, Task.class);
                if (scheduled == null) {
                    // 如果不是定时任务方法，跳过
                    continue;
                }

                // 设置taskBean信息
                TaskBean taskBean = new TaskBean();
                String methodName = method.getName();
                taskBean.setClassName(className);
                taskBean.setInstanceName(instanceName);
                taskBean.setMethodName(methodName);
                taskBean.setSign(className + "." + methodName);
                taskBean.setCron(scheduled.cron());
                taskBean.setFixedDelay(scheduled.fixedDelay());
                taskBean.setFixedRate(scheduled.fixedRate());
                taskBean.setInitialDelay(scheduled.initialDelay());
                // 设置下一次执行时间
                setNextExecuteTime(taskBean);
                // 描述信息
                if (taskAnnotation != null) {
                    taskBean.setDescription(StringUtils.isEmpty(taskAnnotation.desc()) ? taskAnnotation.desc() : taskAnnotation.value());
                    taskBean.setTime(taskAnnotation.time());
                }

                // 加入到TaskBean容器中
                taskBeanMap.put(taskBean.getSign(), taskBean);
            }
        });
    }

    /**
     * 设置任务下一次执行时间
     *
     * @param taskBean taskBean
     */
    public static void setNextExecuteTime(TaskBean taskBean) {
        if (!StringUtils.isEmpty(taskBean.getCron())) {
            taskBean.setNextExecuteTime(CronUtil.getNextExecution(taskBean.getCron()));
            return;
        }

        if (taskBean.getLastStartTime() == null) {
            // 第一次设置
            LocalDateTime now = LocalDateTime.now();
            taskBean.setNextExecuteTime(taskBean.getInitialDelay() > -1 ? now.plusSeconds(taskBean.getInitialDelay() / 1000) : now);
        } else if (taskBean.getFixedDelay() > -1) {
            LocalDateTime referenceTime = taskBean.getLastSuccessTime() != null ? taskBean.getLastSuccessTime() : taskBean.getLastErrorTime();
            taskBean.setNextExecuteTime(referenceTime.plusSeconds(taskBean.getFixedDelay() / 1000));
        } else if (taskBean.getFixedRate() > -1) {
            taskBean.setNextExecuteTime(taskBean.getLastStartTime().plusSeconds(taskBean.getFixedDelay() / 1000));
        }
    }

    /**
     * 根据类名和方法名获取 taskBean 信息
     *
     * @param className  类名
     * @param methodName 方法名
     * @return taskBean
     */
    public static TaskBean getTaskBean(String className, String methodName) {
        return taskBeanMap.get(className + "." + methodName);
    }

    /**
     * 根据sign获取
     *
     * @param sign sign key值
     * @return taskBean
     */
    public static TaskBean getTaskBeanBySign(String sign) {
        return taskBeanMap.get(sign);
    }

    /**
     * 获取列表
     *
     * @return taskBean list
     */
    public static List<TaskBean> getTaskBeanList() {
        return new ArrayList<>(taskBeanMap.values()).stream().sorted(Comparator.comparing(TaskBean::getSign)).collect(Collectors.toList());
    }

    /**
     * 获取Map
     *
     * @return taskBean Map
     */
    public static Map<String, TaskBean> getTaskBeanMap() {
        return taskBeanMap;
    }
}
