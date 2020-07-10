package org.csu.ffms.domain;


public class Account {
    private String userid;
    private String password;
    private String username;
    private String email;
    private String phone;
    private String familyid;

    public Account() {
    }

    public Account(String userid, String password, String username, String email, String phone, String familyid) {
        this.userid = userid;
        this.password = password;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.familyid = familyid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", familyid='" + familyid + '\'' +
                '}';
    }
}
