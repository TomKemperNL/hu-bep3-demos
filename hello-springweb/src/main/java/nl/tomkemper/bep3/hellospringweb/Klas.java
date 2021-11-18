package nl.tomkemper.bep3.hellospringweb;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Klas {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "klas_id")
    private List<Student> students;

    public Klas(String name) {
        this();
        this.name = name;
    }

    protected Klas() {
        students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void add(Student student) {
        this.students.add(student);
    }

    public void remove(Student student) {
        this.students.remove(student);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(this.students);
    }
}
