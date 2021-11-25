package nl.tomkemper.bep3.whutsupp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;

import java.io.IOException;

public class StudentListener {

    private final Student student;
    private final RemoteForwardingRepository forwarders;

    public StudentListener(Student student, RemoteForwardingRepository forwarders) {
        this.student = student;
        this.forwarders = forwarders;
    }

    public void processMessage(Message message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ChatMessage chatMessage = mapper.readValue(message.getBody(), ChatMessage.class);

        System.out.println(String.format("%s ontving een %s", student.getGivenName(), chatMessage.getContent()));


    }
}
