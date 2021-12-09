package nl.tomkemper.bep3.hellomongo.nestedids;

import java.util.Objects;

public class PersoonId {
    private String id;

    public PersoonId(String id) {
        this.id = id;
    }

    protected PersoonId() {
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersoonId persoonId = (PersoonId) o;
        return id == persoonId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PersoonId{" +
                "id='" + id + '\'' +
                '}';
    }
}
