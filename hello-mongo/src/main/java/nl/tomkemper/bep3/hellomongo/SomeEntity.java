package nl.tomkemper.bep3.hellomongo;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.util.UUID;

public class SomeEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;

    public SomeEntity(String name) {
        this.name = name;

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SomeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
