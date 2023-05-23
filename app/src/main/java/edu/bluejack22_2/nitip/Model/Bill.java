package edu.bluejack22_2.nitip.Model;

public class Bill {

    private String debtorEmail;
    private String lenderEmail;
    private int amount;
    private String status;

    public Bill(String debtorEmail, String lenderEmail, int amount, String status) {
        this.debtorEmail = debtorEmail;
        this.lenderEmail = lenderEmail;
        this.amount = amount;
        this.status = status;
    }

    public Bill() {}

    public String getDebtor_email() {
        return debtorEmail;
    }

    public void setDebtor_email(String debtorEmail) {
        this.debtorEmail = debtorEmail;
    }

    public String getLender_email() {
        return lenderEmail;
    }

    public void setLender_email(String lenderEmail) {
        this.lenderEmail = lenderEmail;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
