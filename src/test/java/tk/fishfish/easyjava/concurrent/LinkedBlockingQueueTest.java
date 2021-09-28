package tk.fishfish.easyjava.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * LinkedBlockingQueue测试
 *
 * @author 奔波儿灞
 * @version 1.0
 */
public class LinkedBlockingQueueTest {

    private final Logger logger = LoggerFactory.getLogger(LinkedBlockingQueueTest.class);

    @Test
    public void run() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1024);
        // 生产者
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // 队列满了后等1s，如果仍然满了，则丢弃
                        boolean offer = queue.offer(UUID.randomUUID().toString(), 1, TimeUnit.SECONDS);
                        if (!offer) {
                            logger.warn("queue full");
                        }
                    } catch (InterruptedException e) {
                        logger.warn("Thread interrupted", e);
                    }
                }
            }
        }).start();
        // 消费者处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String item = queue.poll(1, TimeUnit.SECONDS);
                        if (item == null) {
                            logger.info("no item");
                        } else {
                            // 模拟消费时常
                            Thread.sleep(3000);
                            logger.info("item: {}", item);
                        }
                    } catch (InterruptedException e) {
                        logger.warn("Thread interrupted", e);
                    }
                }
            }
        }).start();
        // 消费者处理，每次提取多个
        new Thread(new Runnable() {
            private final int capacity = 128;
            private final List<String> buffer = new ArrayList<>(capacity);

            @Override
            public void run() {
                while (true) {
                    // 每次提取128个，数据不够时等待500ms
                    int size = queue.drainTo(buffer, capacity);
                    logger.info("drain size: {}", size);
                    buffer.clear();
                    try {
                        Thread.sleep(10000);
                        if (size < capacity) {
                            Thread.sleep(500);
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
