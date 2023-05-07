package edu.bluejack22_2.nitip.Model;

public class TitipDetail {
    private User user;
    private String detail;

    public TitipDetail(User user, String detail) {
        this.user = user;
        this.detail = detail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
