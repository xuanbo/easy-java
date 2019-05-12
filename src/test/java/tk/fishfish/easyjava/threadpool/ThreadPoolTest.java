package tk.fishfish.easyjava.threadpool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class ThreadPoolTest {

    private ExecutorService threadPool;

    @Before
    public void setup() {
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 5;
        TimeUnit unit = TimeUnit.MINUTES;
        int workQueueSize = 1000;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(workQueueSize);
        ThreadFactory threadFactory = new DefaultThreadFactory("threadPool");
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        threadPool = new ThreadPoolExecutor(
                corePoolSize, maxPoolSize,
                keepAliveTime, unit,
                workQueue,
                threadFactory,
                handler
        );
    }

    @Test
    public void run() throws InterruptedException {
        threadPool.execute(() -> System.out.println("run!!!"));
        // 为了等待线程池执行完
        Thread.sleep(3 * 1000);
    }

    @After
    public void cleanup() {
        threadPool.shutdown();
    }

}
