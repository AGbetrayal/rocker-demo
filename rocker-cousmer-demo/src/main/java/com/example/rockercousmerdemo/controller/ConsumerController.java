package com.example.rockercousmerdemo.controller;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author AGbetrayal
 * @date 2020/5/13 21:18
 */
@Component
@RocketMQMessageListener(topic = "test",consumerGroup = "testGroup")
public class ConsumerController implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }
}
