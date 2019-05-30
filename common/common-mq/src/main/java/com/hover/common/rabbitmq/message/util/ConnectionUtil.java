package com.hover.common.rabbitmq.message.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: zhaihx
 * @Description:
 * @Date: Created in 15:52 2018/12/11
 **/
public class ConnectionUtil {

    private static String HOST = "192.168.231.129";
    private static String USERNAME = "XRom";
    private static String PASSWORD = "XRom123";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(HOST);
            connectionFactory.setUsername(USERNAME);
            connectionFactory.setPassword(PASSWORD);
            connectionFactory.setPort(5672);
            try {
                connection = connectionFactory.newConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
