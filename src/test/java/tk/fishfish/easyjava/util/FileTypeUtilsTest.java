package tk.fishfish.easyjava.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * 文件类型工具测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class FileTypeUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(FileTypeUtilsTest.class);

    @Test
    public void isXml() throws IOException {
        ClassPathResource resource = new ClassPathResource("logback-spring.xml");
        BufferedInputStream bis = new BufferedInputStream(resource.getInputStream());
        boolean isXml = FileTypeUtils.isType(bis, FileType.XML);
        LOG.info("isXml: {}", isXml);
    }

}
