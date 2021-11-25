package nl.tomkemper.bep3.whutsupp;

import com.rabbitmq.client.ConnectionFactory;

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

    public void sendMessage() {
        ConnectionFactory cf = new ConnectionFactory();
    }
}
