package helloeventsourcing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloJava implements CommandLineRunner {

    private SomeEntityRepository someEntitites;

    public static void main(String[] args) {
        SpringApplication.run(HelloJava.class, args);
    }

    public HelloJava(SomeEntityRepository someEntitites){
        this.someEntitites = someEntitites;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
