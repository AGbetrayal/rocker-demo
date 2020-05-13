package com.example.rockerproductdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.rockerproductdemo.emtry.Student;
import com.example.rockerproductdemo.repository.StudentRepository;
import com.example.rockerproductdemo.service.TestService;
import io.netty.util.internal.StringUtil;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * @author AGbetrayal
 * @date 2020/5/10 16:21
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    @Override
    public RocketMQLocalTransactionState insert(Message message) {
        System.out.println("执行本地事务");
        Map<String, Object> map = JSON.parseObject(new String((byte[]) message.getPayload()), Map.class);
        Student student = new Student();
        student.setId(1);
        student.setName("xxx");
        Student save = studentRepository.save(student);
        RocketMQLocalTransactionState rocketMQLocalTransactionState = StringUtils.isEmpty(save) ? RocketMQLocalTransactionState.ROLLBACK : RocketMQLocalTransactionState.COMMIT;
//        RocketMQLocalTransactionState rocketMQLocalTransactionState = testMapper.insert(map) ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
        //map.put(id,结果)

        if (String.valueOf(map.get("id")).equals("100")){
            System.out.println("事务报错");
            int i  = 1/0;
        }else  if(String.valueOf(map.get("id")).equals("101")){
            System.out.println("事务挂起");
            rocketMQLocalTransactionState = RocketMQLocalTransactionState.UNKNOWN;
        }
        return rocketMQLocalTransactionState;
    }

    /**
     * controller 发送消息应该调该方法
     * @param map
     */
    @Override
    public void sendTransactionMessage(Map<String, Object> map) {
        Message message = MessageBuilder.withPayload( JSON.toJSONString(map)).build();
        rocketMQTemplate.sendMessageInTransaction("test",message,null);
    }

    @Override
    public int checkTransaction(String id) {
        Optional<Student> byId = studentRepository.findById(Integer.parseInt(id));
        if (byId.isPresent()){
            return 1;
        }
        return 0;
    }
}
