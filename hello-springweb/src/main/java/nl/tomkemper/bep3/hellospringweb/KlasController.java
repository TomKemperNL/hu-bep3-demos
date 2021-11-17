package nl.tomkemper.bep3.hellospringweb;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("students/{klas}")
public class KlasController {

    private KlasRepository repo;

    public KlasController(KlasRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<StudentDTO> getStudents(@PathVariable String klas) {
        Optional<Klas> savedKlas = this.repo.findByName(klas);

        if (savedKlas.isPresent()) {
            //N+1 problem example
            return savedKlas.get().getStudents()
                    .stream().map(s -> new StudentDTO(s.getName(), s.getSlber().getName()))
                    .collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


    }

}
