package nl.tomkemper.bep3.messagingpatterns.producer;

import nl.tomkemper.bep3.messagingpatterns.producer.fireforget.SendEmailCommand;
import nl.tomkemper.bep3.messagingpatterns.producer.requestreply.RequestReplyProducer;
import nl.tomkemper.bep3.messagingpatterns.producer.fireforget.FireForgetProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class DemoRunner implements CommandLineRunner {
    private final FireForgetProducer fireForgetProducer;
    private final RequestReplyProducer requestReplyProducer;

    public DemoRunner(FireForgetProducer fireForgetProducer, RequestReplyProducer requestReplyProducer) {
        this.fireForgetProducer = fireForgetProducer;
        this.requestReplyProducer = requestReplyProducer;
    }

    private void runFireForgetDemo() {
        SendEmailCommand message = new SendEmailCommand(UUID.randomUUID(), "tom@example.com");
        this.fireForgetProducer.sendMessage(message);
    }

    private void runRequestReplyDemo() {
        String key = "bla";
        this.requestReplyProducer.incrementCounter(key);

        int result = this.requestReplyProducer.getCounter(key);
        System.out.printf("Counter %s staat nu op %d%n", key, result);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                System.out.println("Welke gaan we doen? (daarna even op enter drukken)");
                System.out.println("1: Fire and Forget");
                System.out.println("2: Request Reply");
                switch (scanner.nextInt()) {
                    case 1:
                        runFireForgetDemo();
                        break;
                    case 2:
                        runRequestReplyDemo();
                        break;
                }
            }
        }).start();
    }
}
