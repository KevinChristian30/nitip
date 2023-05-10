package edu.bluejack22_2.nitip.Model;

public class GroupRow {
    private String groupName;
    private String groupCode;
    private String lastMessage;
    private String lastTime;

    public GroupRow(String groupName, String groupCode, String lastMessage, String lastTime) {
        this.groupName = groupName;
        this.groupCode = groupCode;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
    }

    public GroupRow(){}

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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
