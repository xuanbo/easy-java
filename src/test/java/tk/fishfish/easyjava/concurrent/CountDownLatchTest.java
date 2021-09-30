package tk.fishfish.easyjava.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch使用
 *
 * @author 奔波儿灞
 * @version 1.0
 */
public class CountDownLatchTest {

    private final Logger logger = LoggerFactory.getLogger(CountDownLatchTest.class);

    @Test
    public void run() {
        CountDownLatch latch = new CountDownLatch(2);
        // thread 1
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("我开始执行...");
                latch.countDown();
                logger.info("我完成了！");
            }
        }).start();
        // thread 1
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("我开始执行...");
                latch.countDown();
                logger.info("我完成了！");
            }
        }).start();
        logger.info("等待大家...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted", e);
        }
        logger.info("大家都完成了！");
    }

}
