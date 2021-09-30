package tk.fishfish.easyjava.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier测试
 *
 * @author 奔波儿灞
 * @version 1.0
 */
public class CyclicBarrierTest {

    private final Logger logger = LoggerFactory.getLogger(ExchangerTest.class);

    @Test
    public void run() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("准备");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    logger.warn("Thread cyclicBarrier await error", e);
                }
                logger.info("结束");
            }
        }).start();
        logger.info("准备");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            logger.warn("Thread cyclicBarrier await error", e);
        }
        logger.info("结束");
    }

}
