package com.hibit2.hibit2.auth.dto;

public class OAuthMember {

    private final String email;
    private final String gender;
    private final int age;

    private String profileImageUrl;

    public OAuthMember(final String email, final String gender, final int age) {
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

}