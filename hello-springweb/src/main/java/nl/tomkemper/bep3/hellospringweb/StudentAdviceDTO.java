package nl.tomkemper.bep3.hellospringweb;

public class StudentAdviceDTO extends StudentDTO {
    private String advice;

    public StudentAdviceDTO(Student student, String advice) {
        super(student);
        this.advice = advice;
    }

    public String getAdvice() {
        return advice;
    }
}
