package common;

import com.hover.common.kafka.config.KafkaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes = {KafkaConfiguration.class})
@RunWith(SpringRunner.class)
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testDemo() throws InterruptedException {
        KafkaConfiguration  kafkaConfiguration = new KafkaConfiguration();
        KafkaTemplate<String, String> kafkaTemplate =  kafkaConfiguration.kafkaTemplate();
        kafkaTemplate.send("test", "this is my first demo");
        //休眠5秒，为了使监听器有足够的时间监听到topic的数据
        Thread.sleep(5000);
    }
}
