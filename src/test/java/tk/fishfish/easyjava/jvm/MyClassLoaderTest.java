package tk.fishfish.easyjava.jvm;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.fishfish.easyjava.Application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 测试自定义ClassLoader
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class MyClassLoaderTest {

    private static final Logger LOG = LoggerFactory.getLogger(MyClassLoaderTest.class);

    @Test
    public void loadClass() throws MalformedURLException, ClassNotFoundException {
        File jar = new File("target/easy-java-1.0.0-SNAPSHOT.jar.original");
        if (jar.isFile()) {
            URL targetUrl = jar.toURI().toURL();
            // 加载，不指定parent，则parent默认为系统类加载器
            MyClassLoader classLoader = new MyClassLoader(new URL[]{targetUrl}, null);
            Class<?> clazz = Class.forName("tk.fishfish.easyjava.Application", true, classLoader);
            LOG.info("equals: {}", clazz.equals(Application.class));
        } else {
            throw new RuntimeException("must be jar");
        }
    }

}
