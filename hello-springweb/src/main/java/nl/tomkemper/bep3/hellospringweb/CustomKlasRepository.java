package nl.tomkemper.bep3.hellospringweb;

import java.util.Optional;

public interface CustomKlasRepository {
    void add(Klas k);

    Optional<Klas> find(String name);

    Optional<SLBer> findSLBer(String name);

    void remove(Klas k);
}
