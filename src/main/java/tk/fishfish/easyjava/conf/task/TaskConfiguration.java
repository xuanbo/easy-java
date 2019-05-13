package tk.fishfish.easyjava.conf.task;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * task配置
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties(TaskProperties.class)
public class TaskConfiguration {

    /**
     * 自定义任务线程池，默认只有一个线程，多个任务无法并发执行
     * 需要为TaskScheduler/ScheduledExecutorService类型
     *
     * @return TaskScheduler
     */
    @Bean
    public TaskScheduler taskScheduler(TaskProperties properties) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 设置线程数
        taskScheduler.setPoolSize(properties.getPoolSize());
        // 设置默认线程名称
        taskScheduler.setThreadNamePrefix(properties.getThreadNamePrefix());
        // 等待所有任务结束后再关闭线程池
        taskScheduler.setWaitForTasksToCompleteOnShutdown(properties.isWaitForTasksToCompleteOnShutdown());
        // 设置拒绝策略
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskScheduler;
    }

}
