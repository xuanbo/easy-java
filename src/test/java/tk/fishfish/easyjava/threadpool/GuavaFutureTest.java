package tk.fishfish.easyjava.threadpool;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Guava Future
 * @author 奔波儿灞
 * @since 1.0
 */
public class GuavaFutureTest {

    private static final Logger LOG = LoggerFactory.getLogger(GuavaFutureTest.class);

    @Test
    public void run() {
        LOG.info("开始");

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);

        final CountDownLatch latch = new CountDownLatch(1);
        ListenableFuture<Integer> future = service.submit(() -> {
            LOG.info("开始耗时计算");
            Thread.sleep(10000);
            LOG.info("结束耗时计算");
            return 100;
        });

        Futures.addCallback(future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                LOG.info("成功，计算结果: {}", result);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LOG.warn("失败", throwable);
                latch.countDown();
            }
        }, executorService);

        LOG.info("结束");
        try {
            // 不让守护线程退出
            latch.await();
        } catch (InterruptedException e) {
            LOG.warn("等待异常", e);
        }
    }

}
