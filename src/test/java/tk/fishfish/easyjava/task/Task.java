package tk.fishfish.easyjava.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 任务
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Component
public class Task {

    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    /**
     * 启动1分钟后，每隔1分钟执行一次
     */
    @Scheduled(initialDelay = 1000 * 60, fixedDelay = 1000 * 60)
    public void doSomething() {
        LOG.info("doSomething...");
    }

}
