package nl.tomkemper.bep3.hellomongo.nestedids;

import java.util.Objects;

public class TelefoonNr {
    public enum NrSoort { Prive, Zakelijk}

    private NrSoort nrSoort;
    private String nummer;

    protected TelefoonNr() {
    }

    public TelefoonNr(NrSoort nrSoort, String nummer) {
        this.nrSoort = nrSoort;
        this.nummer = nummer;
    }

    public NrSoort getNrSoort() {
        return nrSoort;
    }

    public String getNummer() {
        return nummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelefoonNr that = (TelefoonNr) o;
        return nrSoort == that.nrSoort &&
                Objects.equals(nummer, that.nummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nrSoort, nummer);
    }
}
