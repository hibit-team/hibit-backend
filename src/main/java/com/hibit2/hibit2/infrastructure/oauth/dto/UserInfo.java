package com.hibit2.hibit2.infrastructure.oauth.dto;

public class UserInfo {

    private String email;
    private String name;

    private String picture;

    private UserInfo() {
    }

    public UserInfo(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}
