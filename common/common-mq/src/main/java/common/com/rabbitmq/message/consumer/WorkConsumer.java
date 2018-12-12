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
 * Work Queue 工作模式
 * @Author: zhaihx
 * @Date: Created in 9:59 2018/12/12
 **/
public class WorkConsumer {

        private final static String QUEUE_NAME = "test_queue_work";

        public static void main(String[] args) throws IOException {
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(1);//能者多劳模式
            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            //自4.0+ 版本后无法再使用QueueingConsumer，而官方推荐使用DefaultConsumer
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                        throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String message = new String(body,"UTF-8");
                    System.out.println(message);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        System.out.println("1---"+message);
                        doWork(message);
                    }finally {
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }
                }
            };
            //监听队列，当b为true时，为自动提交（只要消息从队列中获取，无论消费者获取到消息后是否成功消息，都认为是消息已经成功消费），
            // 当b为false时，为手动提交（消费者从队列中获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，
            // 如果消费者一直没有反馈，那么该消息将一直处于不可用状态。
            //如果选用自动确认,在消费者拿走消息执行过程中出现宕机时,消息可能就会丢失！！）
            //使用channel.basicAck(envelope.getDeliveryTag(),false);进行消息确认
            channel.basicConsume(QUEUE_NAME,false,consumer);
        }

        private static void doWork(String task) {
            for (char ch : task.toCharArray()) {
                if (ch == '.') {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException _ignored) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
}
