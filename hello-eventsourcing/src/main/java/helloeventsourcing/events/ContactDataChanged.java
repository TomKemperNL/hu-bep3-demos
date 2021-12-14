package helloeventsourcing.events;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ContactDataChanged extends SomeEntityEvent {

    public String email;
    public String phonenr;

    public Long getId() {
        return this.id;
    }

    protected ContactDataChanged() {
        //For Hibernate & Friends
    }

    public ContactDataChanged(String newEmail, String newPhone) {
        this.email = newEmail;
        this.phonenr = newPhone;
    }
}
