package nl.tomkemper.bep3.hellomongo.nestedids;

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
}
