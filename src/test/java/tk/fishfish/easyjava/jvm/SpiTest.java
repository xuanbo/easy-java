package tk.fishfish.easyjava.jvm;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Driver;
import java.util.ServiceLoader;

/**
 * SPI机制
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class SpiTest {

    private static final Logger LOG = LoggerFactory.getLogger(SpiTest.class);

    @Test
    public void loader() {
        ServiceLoader<Driver> driverServiceLoader = ServiceLoader.load(Driver.class);
        for (Driver driver : driverServiceLoader) {
            LOG.info("driver: {}", driver.getClass().getName());
        }
    }

}
