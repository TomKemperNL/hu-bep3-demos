package nl.tomkemper.bep3.whutsupp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import static nl.tomkemper.bep3.whutsupp.Whutsupp.*;

@Component
public class IncomingListener {

    @RabbitListener(queues = INCOMING_QUEUE)
    public void processIncoming(ChatMessage incoming) {
        System.out.println("Coming in:" + incoming.getContent());
    }
}
