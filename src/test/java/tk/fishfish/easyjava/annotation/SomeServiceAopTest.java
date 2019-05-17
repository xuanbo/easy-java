package tk.fishfish.easyjava.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * AOP测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SomeServiceAopTest {

    private static final Logger LOG = LoggerFactory.getLogger(SomeServiceAopTest.class);

    @Autowired
    private SomeService someService;

    @Test
    public void findById() {
        Some some = someService.findById(1L);
        LOG.info("some: {}", some);
    }

}
