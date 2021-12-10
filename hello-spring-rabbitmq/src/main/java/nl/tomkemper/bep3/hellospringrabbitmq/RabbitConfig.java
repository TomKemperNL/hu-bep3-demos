package nl.tomkemper.bep3.hellospringrabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue demoQueue(){
        return QueueBuilder.durable("demo-queue1").build();
    }

    @Bean
    public Queue demoQueue2(){
        return QueueBuilder.durable("demo-queue2").build();
    }

    @Bean
    public Exchange demoExchange(){
        return ExchangeBuilder.directExchange("demo-exchange").build();
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(demoQueue()).to(demoExchange()).with("demo-both").noargs();
    }

    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(demoQueue2()).to(demoExchange()).with("demo-both").noargs();
    }

    @Bean
    MessageConverter getConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
