package com.hover.common.rabbitmq.message.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.hover.common.rabbitmq.message.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: zhaihx
 * @Description:
 * @Date: Created in 9:58 2018/12/12
 **/
public class WorkProducer {

    private static final String QUEUE_NAME="test_queue_work";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Hello World!";
        for (int i = 0; i < 20; i++) {
            message = message + i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        }
        System.out.println("生产者 send ："+message);
        channel.close();
        connection.close();
    }

}
