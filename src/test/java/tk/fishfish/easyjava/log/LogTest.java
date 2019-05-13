package tk.fishfish.easyjava.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试日志
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class LogTest {

    private static final Logger LOG = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void debug() {
        LOG.debug("debug示例，参数：{}", "值");
    }

}
