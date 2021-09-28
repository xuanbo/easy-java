package tk.fishfish.easyjava.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue测试
 *
 * @author 奔波儿灞
 * @version 1.0
 */
public class DelayQueueTest {

    private final Logger logger = LoggerFactory.getLogger(DelayQueueTest.class);

    @Test
    public void run() {
        final DelayQueue<DelayData<String>> queue = new DelayQueue<>();
        // 模拟生产者放入数据
        queue.add(new DelayData<>("ID=1", System.currentTimeMillis() + 5_000));
        queue.add(new DelayData<>("ID=2", System.currentTimeMillis() + 10_000));
        // 消费者处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        DelayData<String> data = queue.poll(1, TimeUnit.SECONDS);
                        if (data == null) {
                            logger.info("no item");
                        } else {
                            logger.info("item: {}", data.getItem());
                        }
                    } catch (InterruptedException e) {
                        logger.warn("Thread interrupted", e);
                    }
                }
            }
        }).start();
        // main等待
        try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            logger.warn("main Thread interrupted", e);
        }
    }

}