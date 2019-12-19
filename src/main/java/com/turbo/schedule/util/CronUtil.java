package com.turbo.schedule.util;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * CronUtil表达式工具类
 *
 * @author zouxq
 */
public class CronUtil {

    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronSequenceGenerator.isValidExpression(cronExpression);
    }

    /**
     * 返回下一个执行时间根据给定的Cron表达式
     *
     * @param cronExpression Cron表达式
     * @return LocalDateTime 下次Cron表达式执行时间
     */
    public static LocalDateTime getNextExecution(String cronExpression) {
        if (cronExpression == null || cronExpression.length() < 1) {
            return null;
        }

        CronSequenceGenerator cron = new CronSequenceGenerator(cronExpression);
        return cron.next(new Date(System.currentTimeMillis())).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    /**
     * 将cronExpression转换成中文
     *
     * @param cronExpression Cron表达式
     * @return 中文
     */
    public static String translateToChinese(String cronExpression) {
        if (cronExpression == null || cronExpression.length() < 1) {
            return "cron表达式为空";
        }

        if (!isValid(cronExpression)) {
            return "corn表达式不正确";
        }
        String[] tmpCorns = cronExpression.split(" ");
        StringBuilder sBuffer = new StringBuilder();
        if (tmpCorns.length == 6) {
            //解析月
            if (!"*".equals(tmpCorns[4])) {
                sBuffer.append(tmpCorns[4]).append("月");
            } else {
                sBuffer.append("每月");
            }
            //解析周
            if (!"*".equals(tmpCorns[5]) && !"?".equals(tmpCorns[5])) {
                char[] tmpArray = tmpCorns[5].toCharArray();
                for (char tmp : tmpArray) {
                    switch (tmp) {
                        case '1':
                            sBuffer.append("星期天");
                            break;
                        case '2':
                            sBuffer.append("星期一");
                            break;
                        case '3':
                            sBuffer.append("星期二");
                            break;
                        case '4':
                            sBuffer.append("星期三");
                            break;
                        case '5':
                            sBuffer.append("星期四");
                            break;
                        case '6':
                            sBuffer.append("星期五");
                            break;
                        case '7':
                            sBuffer.append("星期六");
                            break;
                        case '-':
                            sBuffer.append("至");
                            break;
                        default:
                            sBuffer.append(tmp);
                            break;
                    }
                }
            }

            //解析日
            if (!"?".equals(tmpCorns[3])) {
                if (!"*".equals(tmpCorns[3])) {
                    sBuffer.append(tmpCorns[3]).append("日");
                } else {
                    sBuffer.append("每日");
                }
            }

            //解析时
            if (!"*".equals(tmpCorns[2])) {
                sBuffer.append(tmpCorns[2]).append("时");
            } else {
                sBuffer.append("每时");
            }

            //解析分
            if (!"*".equals(tmpCorns[1])) {
                sBuffer.append(tmpCorns[1]).append("分");
            } else {
                sBuffer.append("每分");
            }

            //解析秒
            if (!"*".equals(tmpCorns[0])) {
                sBuffer.append(tmpCorns[0]).append("秒");
            } else {
                sBuffer.append("每秒");
            }
        }

        return sBuffer.toString();
    }


    public static void main(String[] args) {
        System.out.println(getNextExecution("*/5 * * * * ?"));
    }
}



