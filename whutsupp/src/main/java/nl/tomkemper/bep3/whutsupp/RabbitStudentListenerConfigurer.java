package nl.tomkemper.bep3.whutsupp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitStudentListenerConfigurer implements RabbitListenerConfigurer {
    private final RabbitAdmin admin;
    private final KlasRepository klassen;

    public static final String INCOMING_EXCHANGE = "whutsupp.incoming";
    public static final String INCOMING_QUEUE = INCOMING_EXCHANGE;

    public static final String OUTGOING_EXCHANGE = "whutsupp.outgoing";
    public static final String OUTGOING_QUEUE = OUTGOING_EXCHANGE;

    public static final String PM_EXCHANGE = "whutsupp.pm";
    public static final String ANNOUNCE_EXCHANGE = "whutsupp.announce";

    public RabbitStudentListenerConfigurer(RabbitAdmin admin, KlasRepository klassen) {
        this.admin = admin;
        this.klassen = klassen;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        System.out.println("Woop Woop Konijntjes!");
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

        admin.declareQueue(new Queue(OUTGOING_QUEUE));
        admin.declareBinding(new Binding(
                OUTGOING_QUEUE,
                Binding.DestinationType.QUEUE,
                OUTGOING_EXCHANGE, OUTGOING_QUEUE, null));

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
    }
}
