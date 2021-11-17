package nl.tomkemper.bep3.hellospringweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface KlasRepository extends JpaRepository<Klas, Long> {

    Optional<Klas> findByName(String name);
}
