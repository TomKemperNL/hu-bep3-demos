package nl.tomkemper.bep3.messagingpatterns.consumer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue fireAndForgetQueue() {
        return QueueBuilder.durable("fireforget-example").build();
    }

    @Bean
    public Queue requestCounterQueue() {
        return QueueBuilder.durable("querycounter-example").build();
    }

    @Bean
    public Queue incrementCounterQueue() {
        return QueueBuilder.durable("incrementcounter-example").build();
    }


    @Bean
    public MessageConverter converter() {

        Jackson2JsonMessageConverter converter =  new Jackson2JsonMessageConverter();
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }
}
