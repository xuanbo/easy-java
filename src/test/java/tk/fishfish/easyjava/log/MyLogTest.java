package tk.fishfish.easyjava.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试日志
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyLogTest {

    private static final Logger LOG = LoggerFactory.getLogger(MyLogTest.class);

    @Test
    public void info() {
        LOG.info("info示例，参数：{}", "值");
    }

}
