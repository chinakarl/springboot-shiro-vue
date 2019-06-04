package com.hover.common.kafka.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/6/4
 */

@Component
public class AckDemoListener {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static final Logger log= LoggerFactory.getLogger(DemoListener.class);

    @KafkaListener(id = "ack", topics = "topic.quick.ack",containerFactory = "ackContainerFactory")
    public void ackListener(ConsumerRecord record, Acknowledgment ack) {
        System.out.println("topic.quick.ack receive : " + record.value());
        // 确认接收消息 如果不写 ack.acknowledge() 相当于不确认
        ack.acknowledge();
    }

    /**
     *
     *重新将消息发送到队列中，这种方式比较简单而且可以使用Headers实现第几次消费的功能，用以下次判断
     *
     * @author: zhaihx
     * @date: 14:05 2019/6/4
    **/
    @KafkaListener(id = "ack", topics = "topic.quick.ack", containerFactory = "ackContainerFactory")
    public void ackListener(ConsumerRecord record, Acknowledgment ack, Consumer consumer) {

        //如果偏移量为偶数则确认消费，否则拒绝消费
        if (record.offset() % 2 == 0) {
            log.info(record.offset()+"--ack");
            ack.acknowledge();
        } else {
            log.info(record.offset()+"--nack");
            kafkaTemplate.send("topic.quick.ack", record.value().toString());
        }
    }

    /**
     *
     * 使用Consumer.seek方法，重新回到该未ack消息偏移量的位置重新消费，这种可能会导致死循环，
     * 原因出现于业务一直没办法处理这条数据，但还是不停的重新定位到该数据的偏移量上。
     *
     * @author: zhaihx
     * @date: 14:06 2019/6/4
    **/
    @KafkaListener(id = "ack", topics = "topic.quick.ack", containerFactory = "ackContainerFactory")
    public void ackListener1(ConsumerRecord record, Acknowledgment ack, Consumer consumer) {
        log.info("topic.quick.ack receive : " + record.value());

        //如果偏移量为偶数则确认消费，否则拒绝消费
        if (record.offset() % 2 == 0) {
            log.info(record.offset()+"--ack");
            ack.acknowledge();
        } else {
            log.info(record.offset()+"--nack");
            consumer.seek(new TopicPartition("topic.quick.ack",record.partition()),record.offset() );
        }
    }
}
