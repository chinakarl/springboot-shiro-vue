package com.hover.common.kafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/6/4
 */
@Component
public class ReplayDemoListener {

    private static final Logger log= LoggerFactory.getLogger(ReplayDemoListener.class);

    @KafkaListener(id = "forward", topics = "topic.quick.target")
    @SendTo("topic.quick.real")
    public String forward(String data) {
        System.out.println("topic.quick.target  forward "+data+" to  topic.quick.real");
        return "topic.quick.target send msg : " + data;
    }

}
