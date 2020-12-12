package com.atguigu.blog.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
/**redisson 使用配置
 * */
@Configuration
public class MyRedissonConfig {
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        //创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.56.10:6379");
        // 根据配置 创建 RedissonClient实列
        RedissonClient client = Redisson.create(config);
        return client;
    }
}
