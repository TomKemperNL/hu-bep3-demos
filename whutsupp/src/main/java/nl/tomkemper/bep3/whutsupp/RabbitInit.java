package nl.tomkemper.bep3.whutsupp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

    public RabbitInit(ConnectionFactory cf, ObjectMapper jsonSerializer) {
        this.connectionFactory = cf;
        this.jsonSerializer = jsonSerializer;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Stekker zit erin");
        try (Connection c = this.connectionFactory.createConnection()) {
            try (Channel channel = c.createChannel(false)) {
                channel.exchangeDeclare("whutsupp.pm", BuiltinExchangeType.DIRECT, true);
                channel.exchangeDeclare("whutsupp.announce", BuiltinExchangeType.FANOUT, true);
                channel.exchangeDeclare(INCOMING_EXCHANGE, BuiltinExchangeType.DIRECT, true);
                channel.exchangeDeclare("whutsupp.outgoing", BuiltinExchangeType.DIRECT, true);

                channel.queueDeclare(INCOMING_QUEUE, true, false, false, null);
                channel.queueBind(INCOMING_QUEUE, INCOMING_EXCHANGE, INCOMING_QUEUE);

                if (runTests) {
                    byte[] bytes = this.jsonSerializer.writerFor(ChatMessage.class).writeValueAsBytes(new ChatMessage("Hello World"));
                    channel.basicPublish(INCOMING_QUEUE,INCOMING_QUEUE, true, null, bytes);


                }
            }
        }
    }
}