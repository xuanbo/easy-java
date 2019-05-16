package tk.fishfish.easyjava.threadpool;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * CompletableFuture
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class CompletableFutureTest {

    private static final Logger LOG = LoggerFactory.getLogger(CompletableFutureTest.class);

    @Test
    public void run() {
        LOG.info("开始");
        final CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            LOG.info("开始耗时计算");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOG.warn("执行异常", e);
            }
            LOG.info("结束耗时计算");
            return 100;
        });

        completableFuture.whenComplete((result, e) -> {
            LOG.info("回调结果: {}", result);
            latch.countDown();
        });

        LOG.info("结束");
        try {
            // 不让守护线程退出
            latch.await();
        } catch (InterruptedException e) {
            LOG.warn("等待异常", e);
        }
    }

    @Test
    public void run2() {
        LOG.info("开始");
        final CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture.supplyAsync(() -> {
            LOG.info("开始耗时计算");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOG.warn("执行异常", e);
            }
            LOG.info("结束耗时计算");
            return 100;
        })
                .thenApply((i) -> {
                    LOG.info("i: {}", i);
                    return i * 2;
                })
                .thenApplyAsync((i) -> {
                    LOG.info("i: {}", i);
                    // 可能出现异常
                    throw new RuntimeException();
                })
                .whenComplete((result, e) -> {
                    if (e == null) {
                        LOG.info("回调结果: {}", result);
                    } else {
                        // handler ex
                        LOG.info("执行异常", e);
                    }
                    latch.countDown();
                });

        LOG.info("结束");
        try {
            // 不让守护线程退出
            latch.await();
        } catch (InterruptedException e) {
            LOG.warn("等待异常", e);
        }
    }
}
