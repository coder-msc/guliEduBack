package com.atguigu.tools;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* 引入了RabbitMQ的amqp场景，那RabbitAutoConfiguration就会自动生效
 *
 * */

@EnableRabbit
@SpringBootApplication
public class ToolsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class,args);
    }
}
