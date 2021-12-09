package nl.tomkemper.bep3.hellomongo.nestedids;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class NestedIds implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NestedIds.class, args);
    }

    private MongoTemplate template;

    public NestedIds(MongoTemplate template){
        this.template = template;
    }

    @Override
    public void run(String... args) throws Exception {
        template.dropCollection(Persoon.class);

        Persoon p = new Persoon("Sjakie", "031-123-4567");
        template.save(p);
        System.out.println(p.getId());

        Persoon result = template.findById(p.getId(), Persoon.class);
        System.out.println(result.getNaam());
    }
}
