package com.hover.common.kafka.listener;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/6/3
 */
@Component
public class BatchDemoListener {



    @Bean
    public NewTopic batchTopic() {
        return new NewTopic("topic.quick.batch", 8, (short) 1);
    }

    @KafkaListener(id = "batch",topics = {"topic.quick.batch"},containerFactory = "batchContainerFactory")
    public void batchListener(List<String> data) {
        for (String s : data) {
            System.out.println(  s);
        }
        System.out.println("批次\n\t\r");
    }
}
