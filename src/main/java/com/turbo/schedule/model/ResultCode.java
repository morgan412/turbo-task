package com.turbo.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务状态码枚举
 *
 * @author zouxq
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 操作成功
     */
    SUCCESS(0, "执行成功"),

    /**
     * 业务异常
     */
    FAILURE(-1, "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 消息
     */
    private final String msg;
}
