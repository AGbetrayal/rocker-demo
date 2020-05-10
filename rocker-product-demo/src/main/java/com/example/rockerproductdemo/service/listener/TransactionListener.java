package com.example.rockerproductdemo.service.listener;

import com.alibaba.fastjson.JSON;
import com.example.rockerproductdemo.service.TestService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * @author AGbetrayal
 * @date 2020/5/10 16:32
 */
@RocketMQTransactionListener
public class TransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    TestService testService;

    /**
     * 执行本地事务
     * @param message
     * @param o
     * @return
     */
    @Transactional  // 如果多个事务建议配置事务传播
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            System.out.println("开始执行事务");
            //调用多个事务 并且是异步情况  就要用挂起
            return testService.insert(message);
        }catch (Exception e){
            System.out.println("本地事务回滚");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 事务回调
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        Map map = JSON.parseObject(new String((byte[]) message.getPayload()), Map.class);
        if (testService.checkTransaction(String.valueOf(map.get("id")))>0) {
//        if (testService.checkTransaction(i)>0) {
            System.out.println("事务回调提交");
            return RocketMQLocalTransactionState.COMMIT;
        }
        System.out.println("事务回调回滚");
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
