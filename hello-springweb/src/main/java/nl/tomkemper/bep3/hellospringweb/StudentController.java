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

}
