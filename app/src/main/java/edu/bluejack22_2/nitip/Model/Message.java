package edu.bluejack22_2.nitip.Model;

public class Message {
    private String message;
    private String senderEmail;
    private String groupCode;
    private String timestamp;

    public Message(String message, String senderEmail, String timestamp) {
        this.message = message;
        this.senderEmail = senderEmail;
        this.timestamp = timestamp;
    }

    public Message() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
