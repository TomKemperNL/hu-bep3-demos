package nl.tomkemper.bep3.hellospringweb;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private SLBer slber;

    public Student(String name) {
        this();
        this.name = name;
    }

    protected Student() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public SLBer getSlber() {
        return slber;
    }

    public void setSlber(SLBer slber) {
        this.slber = slber;
    }
}
