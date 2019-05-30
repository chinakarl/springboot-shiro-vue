package com.hover.common.rabbitmq.message.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.hover.common.rabbitmq.message.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * 发布订阅模式 (fanout 交换机类型)
 *
 * @Author: zhaihx
 * @Date: Created in 13:51 2018/12/12
 **/
public class PubSubProducer {

    private static final String EXCHANGE_NAME="test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        /*
            声明exchange交换机
            参数1：交换机名称
            参数2：交换机类型
            参数3：交换机持久性，如果为true则服务器重启时不会丢失
            参数4：交换机在不被使用时是否删除
            参数5：交换机的其他属性
         */
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout", true,true,null);

        String message = "订阅消息";
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println("生产者 send ："+message);
        channel.close();
        connection.close();
    }

}
