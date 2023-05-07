package edu.bluejack22_2.nitip.Model;

public class GroupRow {
    private String groupName;
    private String username;
    private String lastMessage;
    private String lastTime;

    public GroupRow(String groupName, String username, String lastMessage, String lastTime) {
        this.groupName = groupName;
        this.username = username;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
