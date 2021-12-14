package helloeventsourcing;

import helloeventsourcing.events.SomeEntityEvent;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;


@Transactional
public class SomeEntityRepository {

    private EntityManager entities;

    public SomeEntityRepository(EntityManager entities) {
        this.entities = entities;
    }

    public SomeEntity get(UUID id) {
        List<SomeEntityEvent> events = this.entities
                .createQuery("Select e from SomeEntityEvent where c.entityId = :eid order by e.id")
                .setParameter("eid", id)
                .getFirstResult();
    }

    public void save(SomeEntity entity) {
        for (SomeEntityEvent e : entity.getEvents()) {
            this.entities.persist(e);
        }
    }
}
