package common;

import com.hover.common.kafka.config.KafkaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/6/4
 */

@SpringBootTest(classes = {KafkaConfiguration.class})
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.hover"})
public class AckTest {

    @Test
    public void testAck() throws InterruptedException {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        KafkaTemplate<String, String> kafkaTemplate =  kafkaConfiguration.kafkaTemplate();
        for (int i = 0; i < 5; i++) {
            kafkaTemplate.send("topic.quick.ack", i+"");
        }
        Thread.sleep(5000);
    }

}
