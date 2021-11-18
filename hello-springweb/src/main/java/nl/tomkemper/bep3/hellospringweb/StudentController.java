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

    public StudentController(KlasRepository klassen, SLBRepository slbers) {
        this.klassen = klassen;
        this.slbers = slbers;
    }

    @GetMapping
    public List<StudentDTO> getStudents(@PathVariable String klas) {
        Optional<Klas> savedKlas = this.klassen.findByName(klas);

        if (savedKlas.isPresent()) {
            //N+1 problem example
            return savedKlas.get().getStudents()
                    .stream().map(StudentDTO::new)
                    .collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{student}")
    public StudentDTO getStudent(@PathVariable String klas, @PathVariable String student) {
        Optional<Klas> savedKlas = this.klassen.findByName(klas);

        if (savedKlas.isPresent()) {
            Optional<Student> savedStudent = savedKlas.get().getStudents()
                    .stream().filter(s -> s.getName().equalsIgnoreCase(student))
                    .findAny();

            if (savedStudent.isPresent()) {
                return new StudentDTO(savedStudent.get());
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{student}")
    @Transactional
    public StudentDTO putStudent(@PathVariable String klas, @PathVariable String student, @RequestBody StudentDTO data) {
        Optional<Klas> savedKlas = this.klassen.findByName(klas);

        if (savedKlas.isPresent()) {
            Optional<Student> savedStudent = savedKlas.get().getStudents()
                    .stream().filter(s -> s.getName().equalsIgnoreCase(student))
                    .findAny();

            if (savedStudent.isPresent()) {
                Optional<SLBer> slber = this.slbers.findByName(data.getSlb());
                if (slber.isPresent()) {
                    savedStudent.get().setSlber(slber.get());
                    return new StudentDTO(savedStudent.get());
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{student}")
    @Transactional
    public void removeStudent(@PathVariable String klas, @PathVariable String student) {
        Optional<Klas> savedKlas = this.klassen.findByName(klas);

        if (savedKlas.isPresent()) {
            Optional<Student> savedStudent = savedKlas.get().getStudents()
                    .stream().filter(s -> s.getName().equalsIgnoreCase(student))
                    .findAny();

            if (savedStudent.isPresent()) {
                savedKlas.get().remove(savedStudent.get());
            }
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Transactional
    public StudentDTO addStudent(@PathVariable String klas, @RequestBody StudentDTO studentDTO) {
        Optional<Klas> savedKlas = this.klassen.findByName(klas);

        if (savedKlas.isPresent()) {
            Student newStudent = new Student(studentDTO.getStudent());
            if (studentDTO.getSlb() != null && !studentDTO.getSlb().isBlank()) {
                Optional<SLBer> slBer = this.slbers.findByName(studentDTO.getSlb());
                if (slBer.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SLBer not found");
                }
                newStudent.setSlber(slBer.get());
            }

            savedKlas.get().add(newStudent);
            return new StudentDTO(newStudent);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
