package edu.bluejack22_2.nitip.Model;

import java.util.ArrayList;

public class Group {
    private String groupName;
    private String groupCode;
    private ArrayList<User> groupMember;

    public Group(String groupName, String groupCode, ArrayList<User> groupMember) {
        this.groupName = groupName;
        this.groupCode = groupCode;
        this.groupMember = groupMember;
    }

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

    public ArrayList<User> getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(ArrayList<User> groupMember) {
        this.groupMember = groupMember;
    }
}
