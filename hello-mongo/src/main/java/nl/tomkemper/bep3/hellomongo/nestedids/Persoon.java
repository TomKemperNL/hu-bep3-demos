package nl.tomkemper.bep3.hellomongo.nestedids;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Persoon {

    @Id
    private PersoonId id = new PersoonId(42);
    private String naam;
    private List<TelefoonNr> nummers = new ArrayList<>();

    private PersoonId besteVriend = new PersoonId(33);

    protected Persoon(){

    }

    public Persoon(String naam, String privenr){
        this.naam = naam;
        this.nummers.add(new TelefoonNr(TelefoonNr.NrSoort.Prive, privenr));
    }

    public Persoon(String naam, String privenr, String zakelijknr){
        this(naam, privenr);
        this.nummers.add(new TelefoonNr(TelefoonNr.NrSoort.Zakelijk, zakelijknr));
    }

    public PersoonId getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public List<TelefoonNr> getNummers() {
        return Collections.unmodifiableList(nummers);
    }

    public PersoonId getBesteVriend() {
        return besteVriend;
    }
}
