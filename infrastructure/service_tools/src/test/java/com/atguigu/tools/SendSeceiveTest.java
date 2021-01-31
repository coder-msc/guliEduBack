package com.atguigu.tools;

import com.atguigu.tools.entity.UcenterMember;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendSeceiveTest {
    //测试收发消息

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test //测试发送消息
    public void sendMessage(){
        UcenterMember ucenterMember =new UcenterMember();// 对象也可以直接传出  但是必须实现序列化
        ucenterMember.setAvatar("头像");
        ucenterMember.setGmtCreate(new Date());
        String msg="hello java sendMessage";
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",ucenterMember);
        log.info("消息发送成功[{}]",ucenterMember);
    }
}
