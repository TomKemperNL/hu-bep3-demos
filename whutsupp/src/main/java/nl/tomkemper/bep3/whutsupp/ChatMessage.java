package nl.tomkemper.bep3.whutsupp;

import java.util.Optional;

public class ChatMessage {
    private String content;
    private Long senderId;
    private Long receiverId;

    public ChatMessage() {
        this.content = "";
    }

    public ChatMessage(String content) {
        this();
        this.content = content;
    }

    public ChatMessage(String content, long receiverId, long senderId) {
        this();
        this.content = content;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public ChatMessage butForReceiver(long receiverId) {
        return new ChatMessage(this.content, receiverId, this.senderId);
    }
}
