package com.rychkov.eshop.configurations;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    //настраиваем соединение с RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue2() {
        return new Queue("newBooks");
    }

    @Bean
    public Queue myQueue3() {
        return new Queue("editBook");
    }

    @Bean
    public Queue myQueue1() {
        return new Queue("topSellers");
    }

    @Bean
    public Queue myQueue4() {
        return new Queue("deleteBook");
    }

    @Bean
    public Queue myQueue5() {
        return new Queue("addBook");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("books_exchange");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with("newBooks");
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(myQueue1()).to(directExchange()).with("topSellers");
    }

    @Bean
    public Binding binding3() {
        return BindingBuilder.bind(myQueue3()).to(directExchange()).with("editBook");
    }

    @Bean
    public Binding binding4() {
        return BindingBuilder.bind(myQueue4()).to(directExchange()).with("deleteBook");
    }

    @Bean
    public Binding binding5() {
        return BindingBuilder.bind(myQueue5()).to(directExchange()).with("addBook");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
