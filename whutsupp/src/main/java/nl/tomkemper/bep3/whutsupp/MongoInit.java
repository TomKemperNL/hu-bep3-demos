package nl.tomkemper.bep3.whutsupp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

public class MongoInit {
    private final MongoTemplate template;

    public MongoInit(MongoTemplate template) {
        this.template = template;
    }

    public void run(String... args) throws Exception {
        if (Whutsupp.TEST_DATA) {

            template.dropCollection(Klas.class);
            template.dropCollection(RemoteForwarding.class);

            Klas docenten = new Klas("Docenten");
            docenten.students().addAll(List.of(
                    new Student(1, "Tom", "", "Kemper", "tom.kemper@hu.nl"),
                    new Student(2, "Alex", "", "Rothuis", "alex.rothuis@hu.nl"),
                    new Student(3, "Robin", "", "Kuijt", "robin.kuijt@hu.nl")
            ));

            template.insert(docenten);
            template.insert(new RemoteForwarding(3, new RemoteAMQPHost("localhost", 5672, null, null)));
        }
    }
}
