package com.hover.common.rabbitmq.message.producer;

/**
 * @Author: zhaihx
 * @Date: Created in 17:07 2018/12/13
 **/
public abstract class AbstractProducer {

    private String queueName;

    public abstract void producer();

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
