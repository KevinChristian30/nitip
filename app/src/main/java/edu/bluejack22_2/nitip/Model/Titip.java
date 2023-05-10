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

    public String getTitip_name() {
        return titip_name;
    }

    public void setTitip_name(String titip_name) {
        this.titip_name = titip_name;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getEntruster_email() {
        return entruster_email;
    }

    public void setEntruster_email(String entruster_email) {
        this.entruster_email = entruster_email;
    }

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    public ArrayList<TitipDetail> getTitip_detail() {
        return titip_detail;
    }

    public void setTitip_detail(ArrayList<TitipDetail> titip_detail) {
        this.titip_detail = titip_detail;
    }
}
