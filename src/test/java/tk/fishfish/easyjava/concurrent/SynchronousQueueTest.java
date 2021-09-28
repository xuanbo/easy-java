package tk.fishfish.easyjava.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue测试
 *
 * @author 奔波儿灞
 * @version 1.0
 */
public class SynchronousQueueTest {

    private final Logger logger = LoggerFactory.getLogger(SynchronousQueueTest.class);

    @Test
    public void run() throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>(false);
        // 生产者
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("PUT A start");
                    queue.put("A");
                    logger.info("PUT A end");
                } catch (InterruptedException e) {
                    logger.warn("Thread interrupted", e);
                }
            }
        }).start();
        Thread.sleep(1000);
        // 生产者
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("PUT B start");
                    queue.put("B");
                    logger.info("PUT B end");
                } catch (InterruptedException e) {
                    logger.warn("Thread interrupted", e);
                }
            }
        }).start();
        Thread.sleep(1000);
        // take
        try {
            logger.info("TAKE start");
            String item = queue.take();
            logger.info("TAKE end: {}", item);
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted", e);
        }
        Thread.sleep(1000);
    }

}
