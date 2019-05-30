package com.hover.common.rabbitmq.message.consumer;

/**
 * @Author: zhaihx
 * @Date: Created in 16:43 2018/12/13
 **/
public abstract class AbstractConsumer {

    /**
     *
     * 正常消费者
     *
     * @author: zhaihx
     * @date: 16:46 2018/12/13
     **/
    public abstract void consumer();

    /**
     *
     * 带有交换机的消费者
     *
     * @author: zhaihx
     * @date: 16:48 2018/12/13
     * @param exchangeName
     **/
    public abstract void consumer(final String exchangeName);
}
