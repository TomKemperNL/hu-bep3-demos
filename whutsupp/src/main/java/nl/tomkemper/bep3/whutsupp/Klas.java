package nl.tomkemper.bep3.whutsupp;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Klas {
    @Id
    private String name;
    private List<Student> students;

    public String getName() {
        return name;
    }

    public List<Student> students() {
        return this.students;
    }

    protected Klas() {
    }

    public Klas(String naam) {
        this.name = naam;
        this.students = new ArrayList<>();
    }
}
