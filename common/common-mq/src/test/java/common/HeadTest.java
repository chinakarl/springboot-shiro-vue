package common;

import com.hover.common.kafka.config.KafkaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/6/3
 */

@SpringBootTest(classes = {KafkaConfiguration.class})
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.hover"})
public class HeadTest {


    @Test
    public void testAnno() throws InterruptedException {

        KafkaConfiguration  kafkaConfiguration = new KafkaConfiguration();
        KafkaTemplate<String, String> kafkaTemplate =  kafkaConfiguration.kafkaTemplate();

        Map map = new HashMap<>();
        map.put(KafkaHeaders.TOPIC, "topic.quick.anno");
        map.put(KafkaHeaders.MESSAGE_KEY, "0");
        map.put(KafkaHeaders.PARTITION_ID, 0);
        map.put(KafkaHeaders.TIMESTAMP, System.currentTimeMillis());

        ListenableFuture future = kafkaTemplate.send(new GenericMessage<>("test anno listener", map));
        future.addCallback(r->{
            System.out.println("success");
        },e->{
            System.out.println("success");
        });
        Thread.sleep(5000);
    }
}
