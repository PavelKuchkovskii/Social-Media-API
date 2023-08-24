package org.kucher.userservice.dto.message;

import java.util.List;

public class MultipleMessage {

    private String logref;
    private List<Message> messages;

    public MultipleMessage() {
    }

    public MultipleMessage(String logref) {
        this.logref = logref;
    }

    public MultipleMessage(String logref, List<Message> messages) {
        this.logref = logref;
        this.messages = messages;
    }

    public String getLogref() {
        return logref;
    }

    public void setLogref(String logref) {
        this.logref = logref;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
