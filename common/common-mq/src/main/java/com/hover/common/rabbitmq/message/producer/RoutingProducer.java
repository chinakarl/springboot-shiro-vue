package com.hover.common.rabbitmq.message.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.hover.common.rabbitmq.message.util.ConnectionUtil;

/**
 *
 * 路由模式
 *
 * @Author: zhaihx
 * @Date: Created in 14:18 2018/12/12
 **/
public class RoutingProducer {

        private static final String EXCHANGE_NAME = "test_exchange_direct";

        public static void main(String[] argv) throws Exception {
            // 获取到连接以及mq通道
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();

            // 声明exchange,路由模式声明direct
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 消息内容
            String message = "这是消息B";
            channel.basicPublish(EXCHANGE_NAME, "B", null, message.getBytes());
            String messageA = "这是消息A";
            channel.basicPublish(EXCHANGE_NAME, "A", null, messageA.getBytes());
            System.out.println(" [生产者] Sent '" + message + "'");

            channel.close();
            connection.close();
        }

}
