package com.turbo.schedule.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 定时任务属性
 *
 * @author zouxq
 */
@Data
public class TaskBean {

    /**
     * 标识
     */
    private String sign;

    /**
     * 类名
     */
    private String className;

    /**
     * 实例名
     */
    private String instanceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 描述
     */
    private String description;

    /**
     * cron 表达式
     */
    private String cron;

    /**
     * 上一次执行完毕时间点之后多长时间再执行
     */
    private long fixedDelay;

    /**
     * 上一次开始执行时间点之后多长时间再执行
     */
    private long fixedRate;

    /**
     * 第一次延迟多长时间后再执行
     */
    private long initialDelay;

    /**
     * 即执行周期
     */
    private String time;

    /**
     * 上一次开始时间
     */
    private LocalDateTime lastStartTime;

    /**
     * 下一次执行时间
     */
    private LocalDateTime nextExecuteTime;

    /**
     * 上一次执行成功时间
     */
    private LocalDateTime lastSuccessTime;

    /**
     * 上一次执行异常时间
     */
    private LocalDateTime lastErrorTime;

    /**
     * 是否异常
     */
    private boolean isError;

    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 是否正在执行
     */
    private boolean isExecuting;

    /**
     * 是否关闭
     */
    private boolean isClose;

}
