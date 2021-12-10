package nl.tomkemper.bep3.hellospringrabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer2 {

    @RabbitListener(queues = { "demo-queue2" })
    public void consume(SomeMessage message){
        System.out.printf("Ik, consumer2, ontving %s: %s%n", message.getUuid(), message.getContent());
    }
}
