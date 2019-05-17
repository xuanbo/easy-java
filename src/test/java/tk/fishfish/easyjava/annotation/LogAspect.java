package tk.fishfish.easyjava.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * `@Log`切面
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Before(value = "@annotation(log)", argNames = "log")
    public void before(JoinPoint joinPoint, Log log) {
        String module = log.module();
        String function = log.function();
        String description = log.description();
        // 这里我们可以保存到数据库，或者怎么样
        LOG.info("module: {}, function: {}, description: {}", module, function, description);
    }

}
