package com.jia.storage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter getMessageConverter(){
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        return jackson2JsonMessageConverter;
    }

    @Bean(name = "regist")
    public Queue queueRegist(){
        return new Queue("regist");
    }

    @Bean(name = "remove")
    public Queue queueRemove(){
        return new Queue("remove");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("storage.direct");
    }

    @Bean
    public Binding bindingRegist(@Qualifier("regist") Queue queue,DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("regist");
    }

    @Bean
    public Binding bindingRemove(@Qualifier("remove") Queue queue,DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("remove");
    }
}
