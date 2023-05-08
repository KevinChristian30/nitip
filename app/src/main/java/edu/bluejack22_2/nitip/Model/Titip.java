package edu.bluejack22_2.nitip.Model;

import java.util.ArrayList;

public class Titip {
    private String titipName;
    private String closeTime;

    private String entrusterEmail;

    private ArrayList<TitipDetail> titipDetails;

    public Titip(String titipName, String closeTime, ArrayList<TitipDetail> titipDetails) {
        this.titipName = titipName;
        this.closeTime = closeTime;
        this.titipDetails = titipDetails;
    }

    public String getTitipName() {
        return titipName;
    }
    public void setTitipName(String titipName) {
        this.titipName = titipName;
    }
    public String getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
    public ArrayList<TitipDetail> getTitipDetails() {
        return titipDetails;
    }
    public void setTitipDetails(ArrayList<TitipDetail> titipDetails) {
        this.titipDetails = titipDetails;
    }
    public String getEntrusterEmail() {
        return this.entrusterEmail;
    }

    public void setEntrusterEmail(String email) {
        this.entrusterEmail = email;
    }
}
