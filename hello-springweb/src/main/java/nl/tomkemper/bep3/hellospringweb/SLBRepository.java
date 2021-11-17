package nl.tomkemper.bep3.hellospringweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SLBRepository extends JpaRepository<SLBer, Long> {

    Optional<SLBer> findByName(String name);
}
