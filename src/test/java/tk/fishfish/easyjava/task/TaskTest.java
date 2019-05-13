package tk.fishfish.easyjava.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 任务测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskTest {

    @Test
    public void run() throws InterruptedException {
        // 等待5分钟，让task执行
        Thread.sleep(5 * 60 * 1000);
    }

}
