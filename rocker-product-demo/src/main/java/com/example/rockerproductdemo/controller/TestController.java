package com.example.rockerproductdemo.controller;

import com.example.rockerproductdemo.service.TestService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author AGbetrayal
 * @date 2020/5/10 16:10
 */
@RestController
public class TestController {

    @Autowired
    RocketMQTemplate template;

    @Autowired
    private TestService testService;

    @RequestMapping("/test.do")
    public Object test() {
        // template.convertAndSend("test", "测试消息");
        return "操作失败";
    }

    @RequestMapping("/insert.do")
    public Object insert(@RequestParam Map<String,Object> map) {

        testService.sendTransactionMessage(map);

        return "操作成功";
    }
}
