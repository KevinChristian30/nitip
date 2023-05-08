package edu.bluejack22_2.nitip.Model;

import java.util.ArrayList;

public class Group {
    private String group_name;
    private String group_code;
    private ArrayList<User> group_member;

    public Group(String group_name, String group_code, ArrayList<User> group_member) {
        this.group_name = group_name;
        this.group_code = group_code;
        this.group_member = group_member;
    }

    public Group() {

    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    public ArrayList<User> getGroup_member() {
        return group_member;
    }

    public void setGroup_member(ArrayList<User> group_member) {
        this.group_member = group_member;
    }
}
