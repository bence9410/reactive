package com.ben.commentcommon.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

public class RabbitConfig {

    public static final String SAVE_QUEUE_NAME = "comment.save";
    public static final String FIND_QUEUE_NAME = "comment.find";

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue saveQueue() {
        return new Queue(SAVE_QUEUE_NAME, false);
    }

    @Bean
    public DirectExchange saveExchange() {
        return new DirectExchange(SAVE_QUEUE_NAME + ".exc");
    }

    @Bean
    public Binding saveBinding() {
        return BindingBuilder.bind(saveQueue()).to(saveExchange()).with(SAVE_QUEUE_NAME);
    }

    @Bean
    public Queue findQueue() {
        return new Queue(FIND_QUEUE_NAME, false);
    }

    @Bean
    public DirectExchange findExchange() {
        return new DirectExchange(FIND_QUEUE_NAME + ".exc");
    }

    @Bean
    public Binding findBinding() {
        return BindingBuilder.bind(findQueue()).to(findExchange()).with("rpc");
    }
}
