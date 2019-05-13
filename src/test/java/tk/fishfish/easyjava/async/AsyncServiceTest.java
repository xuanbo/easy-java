package tk.fishfish.easyjava.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * async测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AsyncServiceTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void run() throws InterruptedException {
        asyncService.doSomething();
        // 等待5s，让异步任务能执行，防止主线池退出
        Thread.sleep(5 * 1000);
    }

}
