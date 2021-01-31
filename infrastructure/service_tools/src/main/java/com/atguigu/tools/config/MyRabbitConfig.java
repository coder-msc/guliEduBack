package com.atguigu.tools.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitConfig {

    @Bean
    public MessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

    // 消息确认机制
    /*
    * 1 服务器收到消息就回调
    *      1 spring.rabbitmq.publisher-confirms=true
    *      2 设置确认回调confirmCallback
    * 2 消息正确抵达队列 进行回调
    *      1 spring.rabbitmq.publisher-returns=true
    *        spring.rabbitmq.template.mandatory=true
    *      2 设置确认回调ReturnCallback
    *
    *
    * 3 消费端确认（保证每个消息被正确消费 此时才可以将消息从broker中删除）
    *      1 默认是自动确认的 只有消息接收到 服务端 就会移除 消息
    *           问题： 收到很多消息 只有一个成功 剩余的消息会丢失
    *           需要进行手动确认
    *      spring.rabbitmq.listener.simple.acknowledge-mode=manual
    *
    *
    *
    * */


}
