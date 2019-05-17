package tk.fishfish.easyjava.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * classpath
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class ClassPathTest {

    private static final Logger LOG = LoggerFactory.getLogger(ClassPathTest.class);

    @Test
    public void run() throws IOException {
        // maven项目src/main/java、src/test/java、src/main/resources、src/test/resources
        ClassPathResource classPathResource = new ClassPathResource("tk/fishfish/easyjava/io/ClassPathTest.class");
        String path = classPathResource.getFile().getPath();
        LOG.info("path: {}", path);
    }

}
