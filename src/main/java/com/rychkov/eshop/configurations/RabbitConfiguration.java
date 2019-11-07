package com.rychkov.eshop.configurations;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    //настраиваем соединение с RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
    @Bean
    public Queue myQueue1() {
        return new Queue("newBooks");
    }
    @Bean
    public Queue myQueue2() {
        return new Queue("onSale");
    }
    @Bean
    public Queue myQueue3() {
        return new Queue("topSellers");
    }
    @Bean
    public FanoutExchange fanoutExchangeA(){
        return new FanoutExchange("books_fanout");
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(myQueue1()).to(fanoutExchangeA());
    }
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(myQueue2()).to(fanoutExchangeA());
    }
    @Bean
    public Binding binding3(){
        return BindingBuilder.bind(myQueue3()).to(fanoutExchangeA());
    }


}
