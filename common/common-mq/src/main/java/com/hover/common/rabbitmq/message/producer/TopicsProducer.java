package com.hover.common.rabbitmq.message.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.hover.common.rabbitmq.message.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * 通配符
 *
 * @Author: zhaihx
 * @Date: Created in 14:56 2018/12/12
 **/
public class TopicsProducer {

        private static final String EXCHANGE_NAME = "test_exchange_topic";

        public static void main(String[] args) throws IOException, TimeoutException {
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME,"topic");
            String message = "匹配insert";
            channel.basicPublish(EXCHANGE_NAME,"order.update",false,false,null,message.getBytes());

            channel.close();
            connection.close();
        }

}
