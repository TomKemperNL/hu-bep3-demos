package nl.tomkemper.bep3.hellospringweb;

public class StudentDTO {
    private String student;
    private String slb;

    public StudentDTO() {
    }

    public StudentDTO(Student s) {
        this.student = s.getName();
        if (s.getSlber() != null) {
            this.slb = s.getSlber().getName();
        }
    }

    public String getStudent() {
        return student;
    }

    public String getSlb() {
        return slb;
    }
}
