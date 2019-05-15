package tk.fishfish.easyjava.redis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 测试RedisTemplate
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class RedisTemplateTest {

    private static final Logger LOG = LoggerFactory.getLogger(RedisTemplateTest.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void run() {
        final String key = "esayjava";
        final String value = "易学Java";
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(key);

        ops.set(value);
        // 1分钟过期
        ops.expire(1, TimeUnit.MINUTES);

        String v = ops.get();
        LOG.info("v: {}", v);
    }

}
