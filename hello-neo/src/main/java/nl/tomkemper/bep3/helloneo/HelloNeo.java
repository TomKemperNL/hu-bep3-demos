package nl.tomkemper.bep3.helloneo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class HelloNeo implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(HelloNeo.class, args);


    }

    private final KlantRepository klanten;

    public HelloNeo(KlantRepository klanten) {
        this.klanten = klanten;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Lezgooooo");
        Klant smit = new Klant(121, "Smit");
        Klant staal = new Klant(122, "Staal");

        this.klanten.save(smit);
        this.klanten.save(staal);

        Artikel postits = new Artikel(121, "post-its", 2.75);
        Artikel pennen = new Artikel(122, "high light pen", 1.50);
        Artikel diskettes = new Artikel(123, "diskettes 10pk", 3.10);
        Artikel nietmachine = new Artikel(124, "nietmachine", 4.75);
    }
}
