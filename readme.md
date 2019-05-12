# easy-java

整理java技术要点，让java更简单，更容易上手。

## 导航

* [maven](#maven)
* [线程池](#线程池)

## maven

### pom.xml模板

对于maven项目，推荐使用java8、项目编码为utf8。对于国内的开发者而言，为了下载包更快，推荐使用aliyun仓库。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>tk.fishfish</groupId>
    <artifactId>easy-java</artifactId>
    <version>1.0.0</version>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>utf-8</project.build.sourceEncoding>
    </properties>

    <repositories>
        <repository>
            <id>aliyunmaven</id>
            <name>aliyun maven</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        </repository>
    </repositories>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

### maven多模块

对于多模块依赖项目，请看[示例工程](https://github.com/xuanbo/parent)

## 线程池

根据阿里[p3c规范](https://github.com/alibaba/p3c)，推荐通过线程池来创建线程。
而且是手动管理，因为通过`Executors`创建的线程池，存在一些缺陷：

* `FixedThreadPool`和`SingleThreadPool`：允许的请求队列长度为`Integer.MAX_VALUE`，可能会堆积大量的请求，从而导致OOM。
* `CachedThreadPool`和`ScheduledThreadPool`：允许的创建线程数量为`Integer.MAX_VALUE`，可能会创建大量的线程，从而导致OOM。

### 基础

下面是`ThreadPoolExecutor`的构造方法：

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
    if (corePoolSize < 0 ||
        maximumPoolSize <= 0 ||
        maximumPoolSize < corePoolSize ||
        keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.acc = System.getSecurityManager() == null ?
            null :
            AccessController.getContext();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}
```

下面是参数介绍：

| 参数名 | 说明 |
|:------:|:------:|
| corePoolSize | 核心线程池大小 |
| maximumPoolSize | 最大线程池大小 |
| keepAliveTime | 线程最大空闲时间 |
| unit | 时间单位 |
| workQueue | 任务等待队列 |
| threadFactory | 线程创建工厂 |
| handler | 拒绝策略 |

线程池执行策略:

```java
public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();
    /*
        * Proceed in 3 steps:
        *
        * 1. If fewer than corePoolSize threads are running, try to
        * start a new thread with the given command as its first
        * task.  The call to addWorker atomically checks runState and
        * workerCount, and so prevents false alarms that would add
        * threads when it shouldn't, by returning false.
        *
        * 2. If a task can be successfully queued, then we still need
        * to double-check whether we should have added a thread
        * (because existing ones died since last checking) or that
        * the pool shut down since entry into this method. So we
        * recheck state and if necessary roll back the enqueuing if
        * stopped, or start a new thread if there are none.
        *
        * 3. If we cannot queue task, then we try to add a new
        * thread.  If it fails, we know we are shut down or saturated
        * and so reject the task.
        */
    int c = ctl.get();
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
            return;
        c = ctl.get();
    }
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
        if (! isRunning(recheck) && remove(command))
            reject(command);
        else if (workerCountOf(recheck) == 0)
            addWorker(null, false);
    }
    else if (!addWorker(command, false))
        reject(command);
}
```

总结：

* 创建线程直到`corePoolSize`
* 任务放入`workQueue`，直到充满
* 继续创建线程直到`maximumPoolSize`
* 使用拒绝策略`handler`

### 创建

如下创建线程池：

```java
public void create() {
    // 线程池参数
    int corePoolSize = 5;
    int maximumPoolSize = 10;
    long keepAliveTime = 5;
    TimeUnit unit = TimeUnit.MINUTES;
    int workQueueSize = 1000;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(workQueueSize);
    ThreadFactory threadFactory = new DefaultThreadFactory("threadPool");
    RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
    // 创建
    ExecutorService threadPool = new ThreadPoolExecutor(
            corePoolSize, maximumPoolSize,
            keepAliveTime, unit,
            workQueue,
            threadFactory,
            handler
    );
}
```

其中`DefaultThreadFactory`为`ThreadFactory`的一个自定义实现：

```java
package tk.fishfish.easyjava.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认的ThreadFactory
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class DefaultThreadFactory implements ThreadFactory {

    /**
     * 线程组
     */
    private final ThreadGroup group;

    /**
     * 线程编号
     */
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    /**
     * 线程池名称前缀
     */
    private final String namePrefix;

    public DefaultThreadFactory(String namePrefix) {
        SecurityManager manager = System.getSecurityManager();
        this.group = (manager != null) ? manager.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix + "-";
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = namePrefix + threadNumber.getAndIncrement();
        Thread thread = new Thread(group, runnable, name, 0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
```

源码参考JDK默认实现`java.util.concurrent.Executors.DefaultThreadFactory`。

### 补充

JDK默认实现**比较适合cpu密集型任务**，对于IO密集型任务，线程池执行策略需要调整：

* 优先充满线程直到`maximumPoolSize`
* 任务放入`workQueue`，直到充满
* 使用拒绝策略`handler`

可以参考新浪RPC框架`motan`的实现，其中需要自定义线程池与工作队列。

* 线程池`StandardThreadExecutor`

    ```java
    package tk.fishfish.easyjava.threadpool;

    import java.util.concurrent.RejectedExecutionException;
    import java.util.concurrent.RejectedExecutionHandler;
    import java.util.concurrent.ThreadFactory;
    import java.util.concurrent.ThreadPoolExecutor;
    import java.util.concurrent.TimeUnit;
    import java.util.concurrent.atomic.AtomicInteger;

    /**
    * 优先启动线程
    *
    * 代码来自motan rpc
    *
    * @author 奔波儿灞
    * @since 1.0
    */
    public class StandardThreadExecutor extends ThreadPoolExecutor {

        public static final int DEFAULT_MIN_THREADS = 20;

        public static final int DEFAULT_MAX_THREADS = 200;

        /**
        *  1 minutes
        */
        public static final int DEFAULT_MAX_IDLE_TIME = 60 * 1000;

        /**
        * 正在处理的任务数
        */
        private AtomicInteger submittedTasksCount;

        /**
        * 最大允许同时处理的任务数
        */
        private int maxSubmittedTaskCount;

        public StandardThreadExecutor(int coreThreads, int maxThreads, long keepAliveTime, TimeUnit unit,
                                    int queueCapacity, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(coreThreads, maxThreads, keepAliveTime, unit, new ExecutorQueue(), threadFactory, handler);
            ((ExecutorQueue) getQueue()).setStandardThreadExecutor(this);

            submittedTasksCount = new AtomicInteger(0);

            // 最大并发任务限制： 队列buffer数 + 最大线程数
            maxSubmittedTaskCount = queueCapacity + maxThreads;
        }

        @Override
        public void execute(Runnable command) {
            int count = submittedTasksCount.incrementAndGet();

            // 超过最大的并发任务限制，进行 reject
            // 依赖的LinkedTransferQueue没有长度限制，因此这里进行控制
            if (count > maxSubmittedTaskCount) {
                submittedTasksCount.decrementAndGet();
                getRejectedExecutionHandler().rejectedExecution(command, this);
            }

            try {
                super.execute(command);
            } catch (RejectedExecutionException rx) {
                // there could have been contention around the queue
                if (!((ExecutorQueue) getQueue()).force(command)) {
                    submittedTasksCount.decrementAndGet();

                    getRejectedExecutionHandler().rejectedExecution(command, this);
                }
            }
        }

        public int getSubmittedTasksCount() {
            return this.submittedTasksCount.get();
        }

        public int getMaxSubmittedTaskCount() {
            return maxSubmittedTaskCount;
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            submittedTasksCount.decrementAndGet();
        }

    }
    ```

* 任务队列`ExecutorQueue`

    ```java
    package tk.fishfish.easyjava.threadpool;

    import java.util.concurrent.LinkedTransferQueue;
    import java.util.concurrent.RejectedExecutionException;

    /**
    * LinkedTransferQueue 能保证更高性能，相比与LinkedBlockingQueue有明显提升
    * <p>
    * 1) 不过LinkedTransferQueue的缺点是没有队列长度控制，需要在外层协助控制
    * <p>
    * 代码来自motan rpc
    *
    * @author 奔波儿灞
    * @since 1.0
    */
    public class ExecutorQueue extends LinkedTransferQueue<Runnable> {

        private StandardThreadExecutor threadPoolExecutor;

        public ExecutorQueue() {
            super();
        }

        public void setStandardThreadExecutor(StandardThreadExecutor threadPoolExecutor) {
            this.threadPoolExecutor = threadPoolExecutor;
        }

        /**
        * 注：代码来源于 tomcat
        *
        * @param runnable Runnable
        * @return 是否添加成功
        */
        public boolean force(Runnable runnable) {
            if (threadPoolExecutor.isShutdown()) {
                throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
            }
            // forces the item onto the queue, to be used if the task is rejected
            return super.offer(runnable);
        }

        /**
        * 注：tomcat的代码进行一些小变更
        * 在提交的任务数超过poolSize, 而poolSize小于最大线程数的时候返回false, 让executor创建线程
        *
        * @param runnable Runnable
        * @return if the runnable was added to this queue
        */
        @Override
        public boolean offer(Runnable runnable) {
            int poolSize = threadPoolExecutor.getPoolSize();

            // we are maxed out on threads, simply queue the object
            if (poolSize == threadPoolExecutor.getMaximumPoolSize()) {
                return super.offer(runnable);
            }
            // we have idle threads, just add it to the queue
            // note that we don't use getActiveCount(), see BZ 49730
            if (threadPoolExecutor.getSubmittedTasksCount() <= poolSize) {
                return super.offer(runnable);
            }
            // if we have less threads than maximum force creation of a new thread
            if (poolSize < threadPoolExecutor.getMaximumPoolSize()) {
                return false;
            }
            // if we reached here, we need to add it to the queue
            return super.offer(runnable);
        }

    }
    ```

参考：

* [motan](https://github.com/weibocom/motan)
* [Motan在服务provider端用于处理request的线程池](https://www.cnblogs.com/huangll99/p/6661235.html)

### spring boot集成

以spring boot为例，这里主要介绍：

* task：可以认为是quartz的简化版本
* async：将任务提交到线程池，异步执行

#### spring task

使用注解`@Scheduled`即可，十分的方便，用于执行一些简单的、固定的任务。

```java
/**
 * 启动1分钟后，每隔1分钟执行一次
 */
@Scheduled(initialDelay = 1000 * 60, fixedDelay = 1000 * 60)
public void doSomething() {
    // 执行任务
}
```

但是**存在的问题是单线程，不能多任务并行执行**，因此我们需要自定义任务线程池。

我们只需要创建`TaskScheduler`或`ScheduledExecutorService`类型的bean即可，spring会将其作为的任务线程池。

```text
2019-05-12 18:02:20.315  INFO 73998 --- [           main] s.a.ScheduledAnnotationBeanPostProcessor : No TaskScheduler/ScheduledExecutorService bean found for scheduled processing
```

下面是任务的配置，通过`ThreadPoolTaskScheduler`，我们可以设置是否等待任务结束再退出程序。

```java
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * task配置
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties(TaskProperties.class)
public class TaskConfiguration {

    /**
     * 自定义任务线程池，默认只有一个线程，多个任务无法并发执行
     * 需要为TaskScheduler/ScheduledExecutorService类型
     *
     * @return TaskScheduler
     */
    @Bean
    public TaskScheduler taskScheduler(TaskProperties properties) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 设置线程数
        taskScheduler.setPoolSize(properties.getPoolSize());
        // 设置默认线程名称
        taskScheduler.setThreadNamePrefix(properties.getThreadNamePrefix());
        // 等待所有任务结束后再关闭线程池
        taskScheduler.setWaitForTasksToCompleteOnShutdown(properties.isWaitForTasksToCompleteOnShutdown());
        // 设置拒绝策略
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskScheduler;
    }

}
```

通过自定义`TaskProperties`，我们可以通过配置文件定义线程池参数，而不需要改代码。

```java
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * task properties
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@ConfigurationProperties(TaskProperties.PREFIX)
public class TaskProperties {

    public static final String PREFIX = "task";

    /**
     * 线程数
     */
    private int poolSize = 1;

    /**
     * 线程空闲时间
     */
    private int keepAliveSeconds = 60;

    /**
     * 是否等待任务结束再退出
     */
    private boolean waitForTasksToCompleteOnShutdown = true;

    /**
     * 线程前缀
     */
    private String threadNamePrefix = PREFIX;

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public boolean isWaitForTasksToCompleteOnShutdown() {
        return waitForTasksToCompleteOnShutdown;
    }

    public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }
}
```

例如，在配置文件中：

```yml
# 任务线程池
task:
  pool-size: 2
  keep-alive-seconds: 60
  wait-for-tasks-to-complete-on-shutdown: true
  thread-name-prefix: taskPool-
```

#### spring async

我们可以通过在方法上面添加`@Async`，将方法异步化（`@EnableAsync`注解开启异步）。即方法会提交到异步线程池中执行，比较适合耗时的任务，而前端又需要立即返回。

```java
@Service
public class DempServiceImpl implements DemoService {

    private static final Logger LOG = LoggerFactory.getLogger(DempServiceImpl.class);

    @Async
    public void say() {
        try {
            LOG.info("say start");
            // 模拟执行很久的一个任务
            Thread.sleep(10000);
            LOG.info("say end");
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
```

当然，与task类似的是，我们也可以自定义异步执行的线程池。

其实只要配置一个类型为`TaskExecutor`，bean的名称为`taskExecutor`的Bean即可。

```java
@Bean
public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // 线程池前缀，可以随意指定。
    executor.setThreadNamePrefix("myAsyncExecutor");
    // 设置线程池参数
    executor.setCorePoolSize(1);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(100);
    // 设置拒绝策略，由调用者执行
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    return executor;
}
```

完整探究过程，看我整理的[Spring Boot使用@Async](http://www.fishfish.tk/article/5)即可。