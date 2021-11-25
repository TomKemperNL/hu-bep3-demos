package nl.tomkemper.bep3.whutsupp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final KlasRepository klassen;
    private final RabbitTemplate rabbitTemplate;
    private final RemoteForwardingRepository forwarders;

    public ChatController(KlasRepository klassen, RemoteForwardingRepository forwarders, RabbitTemplate rabbitTemplate) {
        this.klassen = klassen;
        this.rabbitTemplate = rabbitTemplate;
        this.forwarders = forwarders;
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

    @PostMapping("all")
    public void postAnnouncement(@RequestBody ChatMessage message) {
        if (message.getContent() == null || message.getSenderId() == null || message.getContent().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        rabbitTemplate.convertAndSend(RabbitInit.ANNOUNCE_EXCHANGE, "...", message);
    }

    @PostMapping("{studentnr}")
    public void postMessage(@RequestBody ChatMessage message, @PathVariable Long studentnr) {
        if (message.getContent() == null || message.getSenderId() == null || message.getContent().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Klas> klas = this.klassen.findKlasByStudent(studentnr);
        if (klas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Optional<Student> student = klas.get().findStudent(studentnr);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        rabbitTemplate.convertAndSend(RabbitInit.PM_EXCHANGE, student.get().getRoutingKey(), message.butForReceiver(studentnr));
    }
}
