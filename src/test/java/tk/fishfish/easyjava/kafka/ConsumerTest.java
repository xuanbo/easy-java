package tk.fishfish.easyjava.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 消费者
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerTest {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerTest.class);

    @KafkaListener(topics = "test")
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
        LOG.info("record: {}", record);
        String value = record.value();
        if (value.length() % 2 == 0) {
            throw new RuntimeException("模拟业务出错");
        }
        // 业务处理成功确认
        ack.acknowledge();
    }

    @Test
    public void run() {
        try {
            // 阻塞5分钟，方便调试
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            LOG.warn("sleep error", e);
        }
    }

}
