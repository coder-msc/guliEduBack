package com.atguigu.tools.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.tools.entity.ESBlogDTO;
import com.atguigu.tools.entity.UcenterMember;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReceiveTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    /*
    @RabbitListener 可以标到类 方法上
    @RabbitHander 只可以标在方法上
    * */

    //    @RabbitListener(queues = {"hello-java-queues"})
    //第二个参数 会将JSON自动转换 成对象
    public void receiveMessage(Message message, UcenterMember content, Channel channel) throws IOException {
        System.out.println("收到消息{}" + content);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);//手动确认
//        channel.basicNack(deliveryTag,false,false); //拒绝确认 发回到队列中
    }
/*
*  将接收到的消息放入ES中
* */
    @RabbitListener(queues = {"hello-java-queues"})
    public void receiveMQMessageToES(Message message, ESBlogDTO content, Channel channel) throws IOException {
        System.out.println("收到消息{}" + content);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
             bulkRequest.add(new IndexRequest("jd_goods").source(JSON.toJSONString(content), XContentType.JSON));
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);//手动确认
        System.out.println("将消息放入到ES中{}" + content);

//        channel.basicNack(deliveryTag,false,false); //拒绝确认 发回到队列中
    }
}
