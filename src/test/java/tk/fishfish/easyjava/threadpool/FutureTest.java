package tk.fishfish.easyjava.threadpool;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * JDK Future
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class FutureTest {

    private static final Logger LOG = LoggerFactory.getLogger(FutureTest.class);

    @Test
    public void run() {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        Future<Integer> future = threadPool.submit(() -> {
            Thread.sleep(10000);
            // 结果
            return 100;
        });

        // do something

        try {
            // main阻塞等待结果
            Integer result = future.get();
            LOG.info("result: {}", result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            LOG.warn("获取结果异常", e);
        }
    }

}
