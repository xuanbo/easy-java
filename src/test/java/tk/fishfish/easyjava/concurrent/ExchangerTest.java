package tk.fishfish.easyjava.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.Exchanger;

/**
 * Exchanger测试
 *
 * @author 奔波儿灞
 * @version 1.0
 */
public class ExchangerTest {

    private final Logger logger = LoggerFactory.getLogger(ExchangerTest.class);

    @Test
    public void run() {
        Exchanger<String> exchanger = new Exchanger<>();
        // 生产者
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String data = exchanger.exchange(UUID.randomUUID().toString());
                        logger.info("投递给: {}", data);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.warn("Thread interrupted", e);
                    }
                }
            }
        }).start();
        // 消费者
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String data = exchanger.exchange("Consumer-1");
                        logger.info("接收到: {}", data);
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
