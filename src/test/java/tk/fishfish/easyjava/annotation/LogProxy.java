package tk.fishfish.easyjava.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Log代理
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class LogProxy implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LogProxy.class);

    /**
     * 被代理的类
     */
    private Object target;

    @SuppressWarnings("unchecked")
    public <T> T bind(T target) {
        this.target = target;
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这一步获取实际类的方法，因为method是接口的方法时，是获取不到实现类上的注解信息的
        Method realMethod = getRealMethod(method);
        // 查找方法上是否存在该注解
        Log log = realMethod.getDeclaredAnnotation(Log.class);
        if (log == null) {
            LOG.debug("方法: {} 无@Log注解", method);
        } else {
            String module = log.module();
            String function = log.function();
            String description = log.description();
            // 这里我们可以保存到数据库，或者怎么样
            LOG.info("module: {}, function: {}, description: {}", module, function, description);
        }
        return method.invoke(target, args);
    }

    private Method getRealMethod(Method method) throws NoSuchMethodException {
        return target.getClass().getMethod(method.getName(), method.getParameterTypes());
    }
}
