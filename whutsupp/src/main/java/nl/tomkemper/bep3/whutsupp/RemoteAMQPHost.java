package nl.tomkemper.bep3.whutsupp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;

public class RemoteAMQPHost {
    private String hostname;
    private int port;

    private String user;
    private String password; //don't try this at home (plain text passwords)

    public RemoteAMQPHost(String hostname, int port, String user, String password) {
        this.hostname = hostname;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public org.springframework.amqp.rabbit.connection.ConnectionFactory createConnectionFactory() {
        com.rabbitmq.client.ConnectionFactory cf = new com.rabbitmq.client.ConnectionFactory();
        cf.setHost(this.hostname);
        cf.setPort(this.port);
        if (this.user != null) {
            cf.setUsername(this.user);
        }
        if (this.password != null) {
            cf.setPassword(this.password);
        }
        return new CachingConnectionFactory(cf);
    }
}
