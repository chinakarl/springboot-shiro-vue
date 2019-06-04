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
public class ReplayTest {

    @Test
    public void testForward() throws InterruptedException {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        KafkaTemplate<String, String> kafkaTemplate =  kafkaConfiguration.kafkaTemplate();
        kafkaTemplate.send("topic.quick.target", "test @SendTo");
        Thread.sleep(5000);
    }
}
