package helloeventsourcing.events;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class NameTypoFixed extends SomeEntityEvent {


    public String name;

    public Long getId(){
        return this.id;
    }

    protected NameTypoFixed(){
        //for Hibernate & Friends
    }

    public NameTypoFixed(String newName){
        this.name = name;
    }
}
