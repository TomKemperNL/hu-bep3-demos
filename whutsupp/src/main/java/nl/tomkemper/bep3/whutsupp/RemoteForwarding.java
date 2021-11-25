package nl.tomkemper.bep3.whutsupp;

import org.springframework.data.annotation.Id;

public class RemoteForwarding {
    @Id
    private long studentNr;
    private RemoteAMQPHost remoteHost;

    public RemoteForwarding(long studentNr, RemoteAMQPHost remoteHost) {
        this.studentNr = studentNr;
        this.remoteHost = remoteHost;
    }

    public long getStudentNr() {
        return studentNr;
    }

    public RemoteAMQPHost getRemoteHost() {
        return remoteHost;
    }
}
