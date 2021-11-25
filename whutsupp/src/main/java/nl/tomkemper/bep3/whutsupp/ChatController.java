package nl.tomkemper.bep3.whutsupp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final KlasRepository klassen;
    private final RabbitTemplate rabbitTemplate;

    public ChatController(KlasRepository klassen, RabbitTemplate rabbitTemplate) {
        this.klassen = klassen;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("{studentnr}")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable Long studentnr) {
        Optional<Klas> klas = this.klassen.findKlasByStudent(studentnr);
        if (klas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Student> student = klas.get().findStudent(studentnr);
        if (student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ChatMessage> newMessages = new ArrayList<>();

        Object message = rabbitTemplate.receiveAndConvert(student.get().getRoutingKey());
        while (message != null) {
            newMessages.add((ChatMessage) message);
            message = rabbitTemplate.receiveAndConvert(student.get().getRoutingKey());
        }

        return ResponseEntity.ok(newMessages);
    }
}