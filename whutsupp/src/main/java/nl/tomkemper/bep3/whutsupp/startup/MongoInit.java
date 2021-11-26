package nl.tomkemper.bep3.whutsupp.startup;

import nl.tomkemper.bep3.whutsupp.Klas;
import nl.tomkemper.bep3.whutsupp.Student;
import nl.tomkemper.bep3.whutsupp.Whutsupp;
import nl.tomkemper.bep3.whutsupp.forwarding.RemoteForwarding;
import nl.tomkemper.bep3.whutsupp.forwarding.RemoteWhutsuppHost;
import org.springframework.data.mongodb.core.MongoTemplate;

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
            //template.insert(new RemoteForwarding(3, new RemoteWhutsuppHost("localhost", 8080)));
        }
    }
}
