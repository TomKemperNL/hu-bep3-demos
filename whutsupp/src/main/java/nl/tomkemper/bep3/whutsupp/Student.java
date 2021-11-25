package nl.tomkemper.bep3.whutsupp;

import org.springframework.data.annotation.Id;

public class Student {

    @Id
    private long studentNr;
    private String givenName;
    private String prefix;
    private String surname;
    private String email;

    public Student(long studentNr, String givenName, String prefix, String surname, String email) {
        this.studentNr = studentNr;
        this.givenName = givenName;
        this.prefix = prefix;
        this.surname = surname;
        this.email = email;
    }


    public long getStudentNr() {
        return studentNr;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getRoutingKey() {
        return String.format("%s.%s", RabbitInit.PM_EXCHANGE, this.getStudentNr()); //ongewenste koppeling...
    }
}
