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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Order(2)
public class RabbitInit implements CommandLineRunner {

    public static final String INCOMING_EXCHANGE = "whutsupp.incoming";
    public static final String INCOMING_QUEUE = INCOMING_EXCHANGE;

    public static final String OUTGOING_EXCHANGE = "whutsupp.outgoing";
    public static final String OUTGOING_QUEUE = OUTGOING_EXCHANGE;

    public static final String PM_EXCHANGE = "whutsupp.pm";
    public static final String ANNOUNCE_EXCHANGE = "whutsupp.announce";

    private final RabbitAdmin admin;
    private final RabbitTemplate template;
    private final KlasRepository klassen;

    public RabbitInit(
            RabbitAdmin admin, RabbitTemplate template, KlasRepository klassen) {
        this.admin = admin;
        this.template = template;
        this.klassen = klassen;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Stekker zit erin");
        admin.declareExchange(new DirectExchange(PM_EXCHANGE));
        admin.declareExchange(new FanoutExchange(ANNOUNCE_EXCHANGE));
        admin.declareExchange(new DirectExchange(INCOMING_EXCHANGE));
        admin.declareExchange(new DirectExchange(OUTGOING_EXCHANGE));

        admin.declareQueue(new Queue(INCOMING_QUEUE));
        admin.declareBinding(new Binding(
                INCOMING_QUEUE,
                Binding.DestinationType.QUEUE,
                INCOMING_EXCHANGE, INCOMING_QUEUE, null));

        for (Klas k : this.klassen.findAll()) {
            for (Student s : k.students()) {
                String queueName = s.getRoutingKey();
                admin.declareQueue(new Queue(queueName));
                admin.declareBinding(new Binding(
                        queueName,
                        Binding.DestinationType.QUEUE,
                        PM_EXCHANGE, queueName, null));
                admin.declareBinding(new Binding(
                        queueName,
                        Binding.DestinationType.QUEUE,
                        ANNOUNCE_EXCHANGE, "fanout-" + queueName, null));
            }
        }

        if (Whutsupp.TEST_DATA) {
            this.template.convertAndSend(INCOMING_QUEUE, new ChatMessage("Hello World"));
        }
    }
}