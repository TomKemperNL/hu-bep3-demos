package nl.tomkemper.bep3.hellospringweb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SLBer {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    public SLBer(String name) {
        this();
        this.name = name;
    }

    protected SLBer() {

    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
