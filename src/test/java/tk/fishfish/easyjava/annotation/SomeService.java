package tk.fishfish.easyjava.annotation;

/**
 * 没有卵用的service
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public interface SomeService {

    /**
     * 根据id获取
     *
     * @param id 主键
     * @return 结果，可为空
     */
    Some findById(Long id);

}
