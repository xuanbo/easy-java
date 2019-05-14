package tk.fishfish.easyjava.datasource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.easyjava.datasource.repository.DemoRepository;

/**
 * DemoRepository测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(DemoRepositoryTest.class);

    @Autowired
    private DemoRepository demoRepository;

    @Test
    public void run() {
        Demo demo = new Demo("demo");
        demoRepository.save(demo);
        LOG.info("demo: {}", demo);
    }

}
