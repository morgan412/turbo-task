package com.turbo.schedule.task;

import com.turbo.schedule.model.ITask;
import com.turbo.schedule.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author zouxq
 */
@AllArgsConstructor
@Component
public class DemoTask implements ITask {

    @Task(desc = "任务1", time = "每天上午10点")
    @Scheduled(cron = "0 0 10 * * ?")
    public void pullWorkdayInfo() {
        System.out.println("任务1执行");
    }

    @Task(desc = "任务2", time = "在上一次执行完毕后10秒再次执行")
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 10)
    public void sendSms() {
        System.out.println("任务2执行");
    }
}
