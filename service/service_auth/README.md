## 中间件工具包

### SpringSecurity整合






### RabbitMQ整合
安装命令：
```
docker run -d --name rabbitmq -p 5671:5671 -p 5672:5672 \
-p 4369:4369  -p 25672:25672 -p 15671:15671 -p 15672:15672 \
rabbitmq:management

```
```
端口说明：
4369,25672 (Erlang发现&集群端口）
5672,5671（AMQP端口）
15672（web管理后台端口）
61613，61614（STOMP协议端口）
1883，8883（MQTT协议端口）
```


