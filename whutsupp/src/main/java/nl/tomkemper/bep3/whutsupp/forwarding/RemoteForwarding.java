package nl.tomkemper.bep3.whutsupp.forwarding;

import org.springframework.data.annotation.Id;

public class RemoteForwarding {
    @Id
    private long studentNr;
    private RemoteWhutsuppHost remoteHost;

    public RemoteForwarding(long studentNr, RemoteWhutsuppHost remoteHost) {
        this.studentNr = studentNr;
        this.remoteHost = remoteHost;
    }

    public long getStudentNr() {
        return studentNr;
    }

    public RemoteWhutsuppHost getRemoteHost() {
        return remoteHost;
    }
}
