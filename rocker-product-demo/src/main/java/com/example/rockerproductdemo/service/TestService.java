package com.example.rockerproductdemo.service;

import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import java.util.Map;

/**
 * @author AGbetrayal
 * @date 2020/5/10 16:21
 */
public interface TestService {

    /**
     * 操作事务方法
     * */
    RocketMQLocalTransactionState insert(Message message);

    /**
     * 发送事务消息
     * */
    void  sendTransactionMessage(Map<String,Object> map);

    /**
     * 检查事务
     * */
    int checkTransaction(String id);
}
