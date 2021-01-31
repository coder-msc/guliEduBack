package com.atguigu.tools;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolsTest {
//测试创建队列 交换机 绑定关系
    @Autowired
    AmqpAdmin amqpAdmin;


    /*(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
    * */
    @Test //创建交换机
    public void createExchange(){
        DirectExchange directexchange = new DirectExchange("hello-java-exchange",true,false,null);
        amqpAdmin.declareExchange(directexchange);
        log.info("exchange创建成功[{}]","hello-java-exchange");
    }
    /*(String name, boolean durable, boolean exclusive(排他),
      boolean autoDelete, Map<String, Object> arguments
    * */
    @Test
    public void createQueues(){
        Queue queue = new Queue("hello-java-queues",true,false,false,null);
        amqpAdmin.declareQueue(queue);
        log.info("queues创建成功[{}]","hello-java-queues");
    }

    /*String destination, Binding.DestinationType destinationType,
    String exchange, String routingKey, Map<String, Object> arguments*/
    @Test
    public void creteBinging(){
        Binding binding = new Binding("hello-java-queues", Binding.DestinationType.QUEUE,
                "hello-java-exchange","hello.java",null);
        amqpAdmin.declareBinding(binding);
        log.info("binding创建成功[{}]","hello-java-binding");
    }
}
