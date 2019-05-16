package tk.fishfish.easyjava.threadpool;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Netty-Future
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class NettyFutureTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyFutureTest.class);

    @Test
    public void run() {
        EventExecutorGroup group = new DefaultEventExecutorGroup(4);
        LOG.info("开始");

        final CountDownLatch latch = new CountDownLatch(1);
        Future<Integer> f = group.submit(() -> {
            LOG.info("开始耗时计算");
            Thread.sleep(10000);
            LOG.info("结束耗时计算");
            return 100;
        });

        f.addListener((FutureListener<Integer>) future -> {
            LOG.info("计算结果: {}", future.get());
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
