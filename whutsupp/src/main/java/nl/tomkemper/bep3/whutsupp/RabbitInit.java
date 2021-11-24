package nl.tomkemper.bep3.whutsupp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RabbitInit implements CommandLineRunner {

    public static final String INCOMING_EXCHANGE = "whutsupp.incoming";
    public static final String INCOMING_QUEUE = INCOMING_EXCHANGE;

    private final boolean runTests = true;
    private final ConnectionFactory connectionFactory;
    private final ObjectMapper jsonSerializer;
    private final RabbitAdmin admin;
    private final RabbitTemplate template;

    public RabbitInit(
            RabbitAdmin admin, RabbitTemplate template, ConnectionFactory cf, ObjectMapper jsonSerializer) {
        this.connectionFactory = cf;
        this.jsonSerializer = jsonSerializer;
        this.admin = admin;
        this.template = template;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Stekker zit erin");
        admin.declareExchange(new DirectExchange("whutsupp.pm"));
        admin.declareExchange(new FanoutExchange("whutsupp.announce"));
        admin.declareExchange(new DirectExchange(INCOMING_EXCHANGE));
        admin.declareExchange(new DirectExchange("whutsupp.outgoing"));

        admin.declareQueue(new Queue(INCOMING_QUEUE));
        admin.declareBinding(new Binding(
                INCOMING_QUEUE,
                Binding.DestinationType.QUEUE,
                INCOMING_EXCHANGE, INCOMING_QUEUE, null));

        if (runTests) {
            this.template.convertAndSend(INCOMING_QUEUE, new ChatMessage("Hello World"));
        }
    }
}