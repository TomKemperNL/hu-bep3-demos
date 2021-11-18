package nl.tomkemper.bep3.hellospringweb;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("students/{klas}")
public class StudentController {

    private final KlasRepository klassen;
    private final SLBRepository slbers;
    private final SLBAdviceGenerator advisor;

    public StudentController(KlasRepository klassen, SLBRepository slbers, SLBAdviceGenerator advisor) {
        this.klassen = klassen;
        this.slbers = slbers;
        this.advisor = advisor;
    }


    private Klas findKlas(String klas) {
        Optional<Klas> savedKlas = this.klassen.findByName(klas);
        if (savedKlas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Klas not found");
        }
        return savedKlas.get();
    }

    private Student findStudent(String klas, String student) {
        Klas savedKlas = findKlas(klas);
        Optional<Student> savedStudent = savedKlas.getStudents()
                .stream().filter(s -> s.getName().equalsIgnoreCase(student))
                .findAny();

        if (savedStudent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return savedStudent.get();
    }

    @GetMapping
    public List<StudentDTO> getStudents(@PathVariable String klas) {
        Klas savedKlas = findKlas(klas);
        //N+1 problem example
        return savedKlas.getStudents()
                .stream().map(StudentDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("{student}/advice")
    public StudentAdviceDTO getStudentAdvice(@PathVariable String klas, @PathVariable String student) {
        Student savedStudent = findStudent(klas, student);

        if (savedStudent.getSlber() == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "SLBer not found");
        }
        return new StudentAdviceDTO(
                savedStudent,
                this.advisor.giveAdvice(savedStudent.getSlber(), savedStudent));
    }

    @GetMapping("{student}")
    public StudentDTO getStudent(@PathVariable String klas, @PathVariable String student) {
        Student savedStudent = findStudent(klas, student);
        return new StudentDTO(savedStudent);
    }

    @PutMapping("{student}")
    @Transactional
    public StudentDTO putStudent(@PathVariable String klas, @PathVariable String student, @RequestBody StudentDTO data) {
        Student savedStudent = findStudent(klas, student);
        Optional<SLBer> slber = this.slbers.findByName(data.getSlb());
        if (slber.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SLBer not found");
        }
        savedStudent.setSlber(slber.get());
        return new StudentDTO(savedStudent);
    }

    @DeleteMapping("{student}")
    @Transactional
    public void removeStudent(@PathVariable String klas, @PathVariable String student) {
        Klas savedKlas = findKlas(klas);
        Student savedStudent = findStudent(klas, student);
        savedKlas.remove(savedStudent);
    }

    @PostMapping
    @Transactional
    public StudentDTO addStudent(@PathVariable String klas, @RequestBody StudentDTO studentDTO) {
        Klas savedKlas = findKlas(klas);
        Student newStudent = new Student(studentDTO.getStudent());
        if (studentDTO.getSlb() != null && !studentDTO.getSlb().isBlank()) {
            Optional<SLBer> slBer = this.slbers.findByName(studentDTO.getSlb());
            if (slBer.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SLBer not found");
            }
            newStudent.setSlber(slBer.get());
        }

        savedKlas.add(newStudent);
        return new StudentDTO(newStudent);
    }
}
