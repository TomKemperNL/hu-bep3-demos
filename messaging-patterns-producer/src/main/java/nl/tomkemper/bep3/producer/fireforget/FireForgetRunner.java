package nl.tomkemper.bep3.producer.fireforget;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class FireForgetRunner implements CommandLineRunner {
    private final FireForgetProducer producer;

    public FireForgetRunner(FireForgetProducer producer) {
        this.producer = producer;
        ;
    }

    @Override
    public void run(String... args) throws Exception {
        //... ik wil een beter voorbeeld


        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                SendEmailCommand message = new SendEmailCommand(UUID.randomUUID(), "tom@example.com");
                this.producer.sendMessage(message);
                System.out.println("Druk op Enter voor nog een bericht");
                scanner.nextLine();
            }
        }).start();


    }
}
