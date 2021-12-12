package nl.tomkemper.bep3.hellomongo.uuids;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import nl.tomkemper.bep3.hellomongo.SomeEntity;
import org.bson.UuidRepresentation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class Uuids implements CommandLineRunner {
    private MongoTemplate mongoOps;

    public Uuids(MongoTemplate template) {
        this.mongoOps = template;
    }

    public static void main(String[] args) {
        SpringApplication.run(Uuids.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        mongoOps.dropCollection(SomeEntity.class);


        mongoOps.save(new SomeEntity("Bla"));
        mongoOps.save(new SomeEntity("Bla2"));

        for (SomeEntity e : mongoOps.findAll(SomeEntity.class)) {
            System.out.println(e.getId());
        }
    }
}
