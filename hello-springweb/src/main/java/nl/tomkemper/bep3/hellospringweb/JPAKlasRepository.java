package nl.tomkemper.bep3.hellospringweb;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
public class JPAKlasRepository implements CustomKlasRepository {

    private final EntityManager entities;

    public JPAKlasRepository(EntityManager entities) {
        this.entities = entities;
    }

    @Override
    public void add(Klas k) {
        this.entities.persist(k);
    }

    @Override
    public Optional<Klas> find(String name) {
        Klas result = this.entities.createQuery("SELECT k from Klas k where k.name = :name", Klas.class)
                .setParameter("name", name)
                .getSingleResult();

        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SLBer> findSLBer(String name) {
        SLBer result = this.entities.createQuery("SELECT s from SLBer s where s.name = :name", SLBer.class)
                .setParameter("name", name)
                .getSingleResult();

        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void remove(Klas k) {
        this.entities.remove(k);
    }
}
