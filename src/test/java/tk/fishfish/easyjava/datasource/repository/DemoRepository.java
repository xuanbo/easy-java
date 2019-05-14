package tk.fishfish.easyjava.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.fishfish.easyjava.datasource.Demo;

/**
 * demo Repository
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Repository
public interface DemoRepository extends JpaRepository<Demo, Long> {
}
