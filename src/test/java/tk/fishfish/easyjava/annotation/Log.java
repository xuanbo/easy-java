package tk.fishfish.easyjava.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志注解，用于记录方法调用时日志
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Log {

    /**
     * 模块名称
     *
     * @return 模块名称
     */
    String module();

    /**
     * 功能名称
     *
     * @return 功能名称
     */
    String function();

    /**
     * 描述
     *
     * @return 描述
     */
    String description();

}
