package com.hover.common.kafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/6/3
 */
@Component
public class HeaderDemoListener {

    private static final Logger log= LoggerFactory.getLogger(HeaderDemoListener.class);

    @KafkaListener(topics = {"topic.quick.anno"})
    public void annoListener(@Payload String data,
                             @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts)
    {
        System.out.println("topic.quick.anno receive : \n"+
                "data : "+data+"\n"+
                "key : "+key+"\n"+
                "partitionId : "+partition+"\n"+
                "topic : "+topic+"\n"+
                "timestamp : "+ts+"\n"
        );

    }
}
