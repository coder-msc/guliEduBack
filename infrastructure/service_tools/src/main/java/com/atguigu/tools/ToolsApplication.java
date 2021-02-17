package com.atguigu.tools;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
* 引入了RabbitMQ的amqp场景，那RabbitAutoConfiguration就会自动生效
 *
 * */
@EnableRabbit
@EnableDiscoveryClient
//@EnableFeignClients
@ComponentScan(basePackages = {"com.atguigu.*","com.atguigu.tools.*","com.atguigu.tools.controller"})
@SpringBootApplication
public class ToolsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class,args);
    }
}
