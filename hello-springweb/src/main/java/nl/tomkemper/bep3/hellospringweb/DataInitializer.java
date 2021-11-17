package nl.tomkemper.bep3.hellospringweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final KlasRepository repo;

    public DataInitializer(KlasRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Student tom = new Student("Tom");
        Student alex = new Student("Alex");
        Student robin = new Student("Robin");

        SLBer saskia = new SLBer("Saskia");
        SLBer olga = new SLBer("Olga");

        Klas vkDocenten = new Klas("VKDocenten");

        tom.setSlber(olga);
        alex.setSlber(saskia);
        robin.setSlber(saskia);

        vkDocenten.add(tom);
        vkDocenten.add(alex);
        vkDocenten.add(robin);

        repo.save(vkDocenten);

    }
}
