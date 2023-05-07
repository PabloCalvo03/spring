package com.example.config;

import com.example.messaging.Subscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.UUID;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.setValueSerializer(new GenericToStringSerializer<>(String.class));
//        return template;
//    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
//        return new ChannelTopic(UUID.randomUUID().toString());
    }

    @Bean
    RedisMessageListenerContainer redisContainer(){
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(subscriber(), topic());
        return container;
    }

    @Bean
    MessageListener subscriber() {
        return new Subscriber();
    }

}
