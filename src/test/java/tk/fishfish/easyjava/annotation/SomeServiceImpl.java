package tk.fishfish.easyjava.annotation;

/**
 * 没有卵用的service实现
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class SomeServiceImpl implements SomeService {

    @Override
    // 使用我们定义的注解
    @Log(module = "some", function = "find", description = "根据id查询")
    public Some findById(Long id) {
        // 模拟一些操作
        if (id % 2 == 0) {
            return null;
        }
        return new Some(id, "随机");
    }
}
