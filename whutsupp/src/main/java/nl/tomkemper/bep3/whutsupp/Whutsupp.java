package nl.tomkemper.bep3.whutsupp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Whutsupp {
    public static final boolean TEST_DATA = true;
    public static final String INCOMING_EXCHANGE = "whutsupp.incoming";
    public static final String INCOMING_QUEUE = INCOMING_EXCHANGE;

    public static final String OUTGOING_EXCHANGE = "whutsupp.outgoing";
    public static final String OUTGOING_QUEUE = OUTGOING_EXCHANGE;

    public static final String PM_EXCHANGE = "whutsupp.pm";
    public static final String ANNOUNCE_EXCHANGE = "whutsupp.announce";

    public static void main(String[] args) {
        SpringApplication.run(Whutsupp.class, args);
    }
}
