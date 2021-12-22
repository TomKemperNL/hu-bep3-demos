package nl.tomkemper.bep3.messagingpatterns.producer.requestreply;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RequestReplyProducer {

    private RabbitTemplate rabbit;

    public RequestReplyProducer(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void incrementCounter(String key) {
        this.rabbit.convertAndSend("incrementcounter-example", IncrementCommand.of(key));
    }

    public int getCounter(String key) {
        CounterReply reply = (CounterReply) this.rabbit.convertSendAndReceive("querycounter-example", CounterQuery.of(key));
        return reply.count;
    }

}
