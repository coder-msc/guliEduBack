package com.atguigu.tools.service.impl;

import com.atguigu.tools.entity.UcenterMember;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReceiveTest {

    /*
    @RabbitListener 可以标到类 方法上
    @RabbitHander 只可以标在方法上
    * */

    @RabbitListener(queues = {"hello-java-queues"})
    //第二个参数 会将JSON自动转换 成对象
    public void receiveMessage(Message message, UcenterMember content, Channel channel) throws IOException {
        System.out.println("收到消息{}"+content);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag,false);//手动确认
//        channel.basicNack(deliveryTag,false,false); //拒绝确认 发回到队列中
    }

}
