package nl.tomkemper.bep3.hellomongo.nestedids;

public class PersoonId {
    private long id;

    public PersoonId(long id) {
        this.id = id;
    }

    protected PersoonId() {
    }

    public long getId() {
        return id;
    }

}
