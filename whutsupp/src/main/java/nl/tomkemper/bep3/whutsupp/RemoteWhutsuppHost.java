package nl.tomkemper.bep3.whutsupp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;

public class RemoteWhutsuppHost {
    private String hostname;
    private int port;

    public RemoteWhutsuppHost(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }
}
