package nl.tomkemper.bep3.whutsupp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.io.IOException;
import java.util.Optional;

public class StudentListener {

    private final Student student;
    private final RemoteForwardingRepository forwarders;
    private final RabbitTemplate rabbitTemplate;

    public StudentListener(Student student, RemoteForwardingRepository forwarders, RabbitTemplate rabbitTemplate) {
        this.student = student;
        this.forwarders = forwarders;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processMessage(Message message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ChatMessage chatMessage = mapper.readValue(message.getBody(), ChatMessage.class);
        System.out.println(String.format("%s ontving een %s", student.getGivenName(), chatMessage.getContent()));

        Optional<RemoteForwarding> forwarder = forwarders.findById(this.student.getStudentNr());
        if (forwarder.isEmpty()) {
            System.out.println("We houden 'm lokaal vast");
            this.rabbitTemplate.convertAndSend(Student.getRoutingKey(this.student.getStudentNr()) + ".local", chatMessage);
        } else {
            System.out.println(String.format("We sturen 'm door naar %s", forwarder.get().getRemoteHost()));
            ConnectionFactory cf = forwarder.get().getRemoteHost().createConnectionFactory();
            RabbitTemplate rt = new RabbitTemplate(cf);
            rt.setMessageConverter(new Jackson2JsonMessageConverter());
            rt.convertAndSend(Whutsupp.INCOMING_QUEUE, chatMessage.butForReceiver(this.student.getStudentNr()));
        }
    }
}
