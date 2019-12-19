package com.turbo.schedule.aop;

import com.turbo.schedule.model.TaskBean;
import com.turbo.schedule.model.TaskBeanManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 任务调度方法日志记录 切面
 *
 * @author zouxq
 */
@Slf4j
@Aspect
@Component
public class TaskLogAspect {

    @Around("execution(* com.turbo.schedule.task.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) {
        // 获取类名和方法名
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        // 获取TaskBean信息
        TaskBean taskBean = TaskBeanManager.getTaskBean(className, methodName);

        // 被关闭，不执行
        if (taskBean.isClose()) {
            return null;
        }

        // 正在执行
        if (taskBean.isExecuting()) {
            return null;
        }

        Object result = null;
        try {
            // 开始执行
            taskBean.setExecuting(true);
            taskBean.setLastStartTime(LocalDateTime.now());
            log.info("调度方法【{}】开始执行，方法描述：【{}】", methodName, taskBean.getDescription());

            // 执行目标方法
            result = pjp.proceed();

            // 执行成功
            taskBean.setError(false);
            taskBean.setErrorMsg(null);
            taskBean.setLastSuccessTime(LocalDateTime.now());
            log.info("调度方法【{}】执行结束，方法描述：【{}】", methodName, taskBean.getDescription());
        } catch (Throwable e) {
            // 执行异常
            taskBean.setError(true);
            taskBean.setErrorMsg(e.getMessage());
            taskBean.setLastErrorTime(LocalDateTime.now());
            log.error("调度方法【{}】执行异常，方法描述：【{}】", methodName, taskBean.getDescription());
            e.printStackTrace();
        } finally {
            // 下一次执行时间
            TaskBeanManager.setNextExecuteTime(taskBean);
            taskBean.setExecuting(false);
        }

        return result;
    }

}
