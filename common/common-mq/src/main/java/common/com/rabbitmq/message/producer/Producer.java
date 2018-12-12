package common.com.rabbitmq.message.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import common.com.rabbitmq.message.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: zhaihx
 * @Description:
 * @Date: Created in 16:05 2018/12/11
 **/
public class Producer {

        private static final String QUEUE_NAME="test_queue";

        public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
            //获取连接
            Connection connection = ConnectionUtil.getConnection();
            System.out.println(connection);
            //创建通道
            Channel channel = connection.createChannel(1);
            /*
             * 声明（创建）队列
             * 参数1：队列名称
             * 参数2：为true时server重启队列不会消失
             * 参数3：队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常
             * 参数4：队列不再使用时是否自动删除（没有连接，并且没有未处理的消息)
             * 参数5：建立队列时的其他参数
             */
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String message = "Hello World!";
            for (int i = 0; i < 20; i++) {
                message = message + i;
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                Thread.sleep(1000);
            }
            System.out.println("生产者 send ："+message);
            channel.close();
            connection.close();
        }
}
