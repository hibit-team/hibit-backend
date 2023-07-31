package com.hibit2.hibit2.infrastructure.oauth.dto;

public class UserInfo {

    private String email;
    private String name;

    private UserInfo() {
    }

    public UserInfo(final String email, final String name, final String picture) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
