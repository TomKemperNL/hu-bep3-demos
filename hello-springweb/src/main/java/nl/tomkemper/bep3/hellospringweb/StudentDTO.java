package nl.tomkemper.bep3.hellospringweb;

public class StudentDTO {
    private String student;
    private String slb;

    public StudentDTO(String student, String slb) {
        this.student = student;
        this.slb = slb;
    }

    public String getStudent() {
        return student;
    }

    public String getSlb() {
        return slb;
    }
}
