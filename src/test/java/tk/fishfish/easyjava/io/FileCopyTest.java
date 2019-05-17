package tk.fishfish.easyjava.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件复制
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class FileCopyTest {

    private static final Logger LOG = LoggerFactory.getLogger(FileCopyTest.class);

    @Test
    public void copy() {
        Path source = Paths.get("./readme.md");
        Path target = Paths.get("./readme.md.copy");
        try {
            Files.copy(source, target);
        } catch (IOException e) {
            LOG.error("copy error", e);
        }
    }

}
