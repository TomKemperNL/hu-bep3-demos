package nl.tomkemper.bep3.whutsupp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Whutsupp {
    public static final boolean TEST_DATA = true;

    public static void main(String[] args) {
        SpringApplication.run(Whutsupp.class, args);
    }
}
