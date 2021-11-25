package nl.tomkemper.bep3.whutsupp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.client.RestTemplate;

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

            RestTemplate rt = new RestTemplate();
            String host = forwarder.get().getRemoteHost().getHostname();
            int port = forwarder.get().getRemoteHost().getPort();

            try {
                String url = String.format("http://%s:%s/chat/%s", host, port, this.student.getStudentNr());
                rt.postForLocation(url, chatMessage.butForReceiver(this.student.getStudentNr()));
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}
