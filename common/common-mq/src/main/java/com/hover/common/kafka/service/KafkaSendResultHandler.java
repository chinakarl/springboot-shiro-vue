package com.hover.common.kafka.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/30
 */

@Component
public class KafkaSendResultHandler  implements ProducerListener<String, String> {

    @Override
    public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata)
    {
        System.out.println("success");
    }

    @Override
    public void onError(String var1, Integer var2, String var3, String var4, Exception var5)
    {
        System.out.println("success");
    }

    @Override
    public boolean isInterestedInSuccess()
    {
        return true;
    }
}
