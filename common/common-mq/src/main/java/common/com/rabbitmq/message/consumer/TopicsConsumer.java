package common.com.rabbitmq.message.consumer;


import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import common.com.rabbitmq.message.util.ConnectionUtil;

import java.io.IOException;

/**
 *
 * 通配符消费者
 *
 * @Author: zhaihx
 * @Date: Created in 14:56 2018/12/12
 **/
public class TopicsConsumer {

        private static final String EXCHANGE_NAME = "test_exchange_topic";

        private  static final String QUEUE_NAME = "test_queue_topic_1";

        public static void main(String[] args) throws IOException {
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            //channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            channel.exchangeDeclare(EXCHANGE_NAME,"topic");
            //order.#
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"order.*");

            channel.basicQos(1);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                        throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    System.out.println(new String(body,"UTF-8"));
                }
            };
            channel.basicConsume(QUEUE_NAME,true,consumer);

        }

}
