package common;

import com.hover.common.kafka.config.KafkaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/6/3
 */
@SpringBootTest(classes = {KafkaConfiguration.class})
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.hover"})
public class BatchListenerTest {

    @Test
    public void batchListener() throws InterruptedException
    {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        KafkaTemplate<String, String> kafkaTemplate =  kafkaConfiguration.kafkaTemplate();
        for (int i = 0; i < 12; i++) {
            ListenableFuture future =kafkaTemplate.send("topic.quick.batch", "test batch listener,dataNum-"+i);
            future.addCallback(r->{
                System.out.println("success");
            },e->{
                System.out.println("error");
            });
        }
        Thread.sleep(5000);
    }
}
