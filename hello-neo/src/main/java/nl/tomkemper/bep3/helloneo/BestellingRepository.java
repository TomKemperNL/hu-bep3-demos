package nl.tomkemper.bep3.helloneo;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BestellingRepository extends CrudRepository<Bestelling, String> {
}
