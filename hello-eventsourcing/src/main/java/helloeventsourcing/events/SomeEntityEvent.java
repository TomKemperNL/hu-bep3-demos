package helloeventsourcing.events;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "SomeEntityEvents")
public abstract class SomeEntityEvent {
    @Id
    protected Long id;
    public UUID entityId;
}
