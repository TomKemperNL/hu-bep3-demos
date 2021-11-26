package nl.tomkemper.bep3.whutsupp.forwarding;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;

public class RemoteWhutsuppHost {

    public enum Protocol {
        AMQP, HTTP
    }

    private final String hostname;
    private final int port;
    private final Protocol protocol;

    public RemoteWhutsuppHost(String hostname, int port, Protocol protocol) {
        this.hostname = hostname;
        this.port = port;
        this.protocol = protocol;
    }

    public RemoteWhutsuppHost(String hostname, int port) {
        this(hostname, port, Protocol.HTTP);
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public Protocol getProtocol() {
        return protocol;
    }

}
