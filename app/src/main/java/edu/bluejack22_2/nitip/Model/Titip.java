package edu.bluejack22_2.nitip.Model;

import java.util.ArrayList;

public class Titip {
    private String titip_name;
    private String close_time;
    private String entruster_email;
    private String group_code;

    private ArrayList<TitipDetail> titip_detail;

    public Titip(String titipName, String closeTime, String groupCode, ArrayList<TitipDetail> titipDetails) {
        this.titip_name = titipName;
        this.close_time = closeTime;
        this.group_code = groupCode;
        this.titip_detail = titipDetails;
    }

    public Titip() {}

    public String getTitipName() {
        return titip_name;
    }
    public void setTitipName(String titipName) {
        this.titip_name = titipName;
    }
    public String getCloseTime() {
        return close_time;
    }
    public void setCloseTime(String closeTime) {
        this.close_time = closeTime;
    }
    public ArrayList<TitipDetail> getTitipDetails() {
        return titip_detail;
    }
    public void setTitipDetails(ArrayList<TitipDetail> titipDetails) {
        this.titip_detail = titipDetails;
    }
    public String getEntruster_email() {
        return this.entruster_email;
    }
    public void setEntruster_email(String email) {
        this.entruster_email = email;
    }
    public String getGroup_code() { return this.group_code;}
    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }
}
