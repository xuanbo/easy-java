package tk.fishfish.easyjava.annotation;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class SomeServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(SomeServiceTest.class);

    private SomeService someService;
    private SomeService proxySomeService;

    @Before
    public void setup() {
        someService = new SomeServiceImpl();
        // 代理
        proxySomeService = new LogProxy().bind(someService);
    }

    @Test
    public void findById() {
        Some some = someService.findById(1L);
        LOG.info("some: {}", some);
    }

    @Test
    public void findByIdProxy() {
        Some some = proxySomeService.findById(1L);
        LOG.info("some: {}", some);
    }

}
