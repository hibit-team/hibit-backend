package com.hibit2.hibit2.domain.member;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hibit2.hibit2.domain.member.exception.InvalidMemberException;

import lombok.Getter;

@Getter
@Entity
public class Member {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");
    private static final int MAX_DISPLAY_NAME_LENGTH = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean gender;

    @Column(nullable = false)
    private int age;


    protected Member() {
    }

    public  Member(final String email, final boolean gender, final int age, final Role role) {
        validateEmail(email);

        this.email = email;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }

    private void validateEmail(final String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if(!matcher.matches()) {
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다.");
        }
    }
}
