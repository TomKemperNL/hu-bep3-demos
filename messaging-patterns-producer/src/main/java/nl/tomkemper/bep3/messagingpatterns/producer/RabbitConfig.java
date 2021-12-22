package nl.tomkemper.bep3.messagingpatterns.producer;

import nl.tomkemper.bep3.messagingpatterns.producer.requestreply.CounterReply;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    /*
        Strict genomen hoeft deze queue niet in de producer gemaakt te worden.
        De producer stuurt naar een exchange: De 'Default Exchange', genaamd "" (lege string) in ons geval.
        De exchange stopt het vervolgens in een queue.

        Als de queue nog niet bestaat, dan accepteert de exchange het gewoon, en vertelt ie je niet dat je bericht nergens
        terecht is gekomen. Dus het gevolg van deze Queue declaren in zowel de producer als de consumer is puur dat
        de demo werkt welke je ook als eerste opstart.

        Als je dat niet zo boeiend vindt kun je ook 'gewoon altijd eerst de consumer starten'.
     */
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
        DefaultJackson2JavaTypeMapper mapper = new DefaultJackson2JavaTypeMapper();
        converter.setJavaTypeMapper(mapper);

        Map<String, Class<?>> extraMappings = new HashMap<>();
        extraMappings.put("nl.tomkemper.bep3.messagingpatterns.consumer.requestreply.CounterReply", CounterReply.class);

        mapper.setIdClassMapping(extraMappings);

        return converter;
    }
}