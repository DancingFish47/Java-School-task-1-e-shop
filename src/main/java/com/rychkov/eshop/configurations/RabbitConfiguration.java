package com.rychkov.eshop.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:properties.properties")
@RequiredArgsConstructor
public class RabbitConfiguration {
    public static final String ADD_QUEUE_NAME = "addBook";
    public static final String EDIT_QUEUE_NAME = "editBook";
    public static final String DELETE_QUEUE_NAME = "deleteBook";
    static final String TOP_SELLERS_QUEUE_NAME = "topSellers";
    static final String NEW_BOOKS_QUEUE_NAME = "newBooks";
    private static final String BOOKS_EXCHANGE_NAME = "books_exchange";
    private final Environment env;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(env.getProperty("rabbit.factory.hostname"));
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
    public Queue newBooksQueue() {
        return new Queue(NEW_BOOKS_QUEUE_NAME);
    }

    @Bean
    public Queue editBookQueue() {
        return new Queue(EDIT_QUEUE_NAME);
    }

    @Bean
    public Queue topSellersQueue() {
        return new Queue(TOP_SELLERS_QUEUE_NAME);
    }

    @Bean
    public Queue deleteBookQueue() {
        return new Queue(DELETE_QUEUE_NAME);
    }

    @Bean
    public Queue addBookQueue() {
        return new Queue(ADD_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(BOOKS_EXCHANGE_NAME);
    }

    @Bean
    public Binding newBooksBinding() {
        return BindingBuilder.bind(newBooksQueue()).to(directExchange()).with(NEW_BOOKS_QUEUE_NAME);
    }

    @Bean
    public Binding topSellersBinding() {
        return BindingBuilder.bind(topSellersQueue()).to(directExchange()).with(TOP_SELLERS_QUEUE_NAME);
    }

    @Bean
    public Binding editBookBinding() {
        return BindingBuilder.bind(editBookQueue()).to(directExchange()).with(EDIT_QUEUE_NAME);
    }

    @Bean
    public Binding deleteBookBinding() {
        return BindingBuilder.bind(deleteBookQueue()).to(directExchange()).with(DELETE_QUEUE_NAME);
    }

    @Bean
    public Binding addBookBinding() {
        return BindingBuilder.bind(addBookQueue()).to(directExchange()).with(ADD_QUEUE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
