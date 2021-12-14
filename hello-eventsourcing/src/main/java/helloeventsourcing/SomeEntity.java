package helloeventsourcing;

import helloeventsourcing.events.*;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class SomeEntity implements SomeEntityEventHandler {
    private UUID id = UUID.randomUUID();
    private Queue<SomeEntityEvent> events = new LinkedBlockingQueue<SomeEntityEvent>();

    private String name;
    private String email;
    private String phonenr;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenr() {
        return phonenr;
    }

    public SomeEntity(String name, String email) {
        this(name, email, null);
    }

    public SomeEntity(String name, String email, String phonenr) {
        this.name = name;
        this.email = email;
        this.phonenr = phonenr;
        this.events.add(new EntityCreated(id, name, email, phonenr));
    }

    public void apply(NameTypoFixed event) {
        this.name = event.newName;
    }

    public void apply(ContactDataChanged event) {
        if (event.email != null) {
            this.email = event.email;
        }
        if (event.phonenr != null) {
            this.phonenr = event.phonenr;
        }
    }

    public static SomeEntity fromCreatedEvent(EntityCreated event) {
        SomeEntity result = new SomeEntity(event.name, event.email, event.phonenr);
        result.id = event.entityId;
        return result;
    }

}
