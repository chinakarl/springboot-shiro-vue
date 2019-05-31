package common;

import com.hover.common.kafka.config.KafkaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes = {KafkaConfiguration.class})
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.hover"})
public class AppTest 
{
//    @Autowired
//    KafkaSendResultHandler sendResultHandler;
    //@Autowired
    //KafkaTemplate<String,String> kafkaTemplate;
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
        //kafkaTemplate.setProducerListener(sendResultHandler);
        ListenableFuture future =kafkaTemplate.send("test", "this is my first demo");
        future.addCallback(r->{
            System.out.println("success");
        },e->{
            System.out.println("error");
        });
        //休眠5秒，为了使监听器有足够的时间监听到topic的数据
        Thread.sleep(5000);
    }

    @Test
    @Transactional
    public void transactionalAnnotation() throws InterruptedException {
        KafkaConfiguration  kafkaConfiguration = new KafkaConfiguration();
        KafkaTemplate<String, String> kafkaTemplate =  kafkaConfiguration.kafkaTemplate();
        kafkaTemplate.send("topic.quick.tran", "test transactional annotation1");
        throw new RuntimeException("fail");
    }

    @Test
    public void testExecuteInTransaction() throws InterruptedException{
        KafkaConfiguration  kafkaConfiguration = new KafkaConfiguration();
        KafkaTemplate<String, String> kafkaTemplate =  kafkaConfiguration.kafkaTemplate();
        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
            @Override
            public Object doInOperations(KafkaOperations kafkaOperations) {
                kafkaOperations.send("topic.quick.tran", "test executeInTransaction2");
                throw new RuntimeException("fail");
                //return true;
            }
        });
    }
}
