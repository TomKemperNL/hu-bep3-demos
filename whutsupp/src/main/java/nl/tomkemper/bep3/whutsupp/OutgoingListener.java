package nl.tomkemper.bep3.whutsupp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import static nl.tomkemper.bep3.whutsupp.Whutsupp.*;

//@Component
public class OutgoingListener {

    private KlasRepository klassen;

    public OutgoingListener(KlasRepository klassen) {
        this.klassen = klassen;
    }

    @RabbitListener(queues = OUTGOING_QUEUE)
    public void processIncoming(ChatMessage outgoing) {
        System.out.println("Going out: " + outgoing.getContent());

    }
}
