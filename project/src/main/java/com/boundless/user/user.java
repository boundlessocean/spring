package com.boundless.user;

import java.io.Serializable;

public class user implements Serializable {
    private String username;
    private String userpass;
    private String nickname;
    private String carname;
    private int id;

    public String getCarname() { return carname;}

    public void setCarname(String carname) { this.carname = carname;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                ", userpass='" + userpass + '\'' +
                ", nickname='" + nickname + '\'' +
                ", carname='" + carname + '\'' +
                ", id=" + id +
                '}';
    }
}
