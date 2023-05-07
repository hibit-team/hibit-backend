package com.hibit2.hibit2.infrastructure.dto;

public class OAuthMember {

    private final String email;
    private final String gender;
    private final int age;
    private final String nickName;
    private final String profileImageUrl;

    public OAuthMember(final String email, final String gender, final int age , final String nickName ,final String profileImageUrl) {
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
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

    public String getNickName() {
        return nickName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}