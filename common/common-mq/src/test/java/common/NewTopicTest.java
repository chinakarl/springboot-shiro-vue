package common;

import com.hover.common.kafka.config.KafkaInitialConfiguration;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/30
 */

@SpringBootTest(classes = {KafkaInitialConfiguration.class})
@RunWith(SpringRunner.class)
public class NewTopicTest {
    @Autowired
    private AdminClient adminClient;

    /**
     *
     * 创建topic
     *
     * @author: zhaihx
     * @date: 17:09 2019/5/30
    **/
    @Test
    public void testCreateTopic() throws InterruptedException {
        NewTopic topic = new NewTopic("topic.quick.initial23", 1, (short) 1);
        adminClient.createTopics(Arrays.asList(topic));
        Thread.sleep(1000);
    }

    /**
     *
     * 获取topic中所有信息
     *
     * @author: zhaihx
     * @date: 17:09 2019/5/30
    **/
    @Test
    public void testSelectTopicInfo() throws ExecutionException, InterruptedException {
        DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList("topic.quick.initial"));
        result.all().get().forEach((k,v)->System.out.println("k: "+k+" ,v: "+v.toString()+"\n\r\t"));
    }
}
