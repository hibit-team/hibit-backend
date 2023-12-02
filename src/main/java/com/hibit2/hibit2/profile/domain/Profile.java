package com.hibit2.hibit2.profile.domain;


import com.hibit2.hibit2.global.config.BaseTimeEntity;
import com.hibit2.hibit2.profile.exception.InvalidNicknameException;
import com.hibit2.hibit2.profile.exception.InvalidPersonalityException;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;

import com.hibit2.hibit2.member.domain.Member;

@Table(name = "profiles")
@Entity
public class Profile extends BaseTimeEntity {

    private static final int MAX_NICK_NAME_LENGTH = 20;
    private static final int MAX_PERSONALITY_COUNT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

    @Column(name = "nickname", length = 20, unique = true)
    private String nickname;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private int gender;

    @Column(name = "personality")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PersonalityType.class)
    private List<PersonalityType> personality;

    @Column(name = "introduce", length = 200)
    private String introduce;

    @Column(name = "main_img", length = 100)
    private String mainImg;

    @ElementCollection
    private List<String> subImg;

    @Column(name = "job", length = 50)
    private String job;

    @Column(name = "address_city")
    @Enumerated(EnumType.STRING)
    private AddressCity addressCity;

    @Column(name = "address_distinct")
    @Enumerated(EnumType.STRING)
    private AddressDistrict addressDistrict;

    @Column(name = "ban")
    private int ban;

    @Column(name = "job_visible")
    private int jobVisible;

    @Column(name = "sub_img_visible")
    private int subImgVisible;

    @Column(name = "address_visible")
    private int addressVisible;

    protected Profile() {
    }

    @Builder
    public Profile(final Member member, final String nickname, final int age, final int gender, final List<PersonalityType> personality,
                   final String introduce, final String mainImg, final List<String> subImg, final String job, final AddressCity addressCity,
                   final AddressDistrict addressDistrict, final int jobVisible, final int subImgVisible, final int addressVisible) {
        validateNickName(nickname);
        validatePersonality(personality);
        this.member = member;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce;
        this.mainImg = mainImg;
        this.subImg = subImg;
        this.job = job;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.jobVisible = jobVisible;
        this.subImgVisible = subImgVisible;
        this.addressVisible = addressVisible;
    }

    private void validateNickName(final String nickname) {
        if(nickname.isEmpty() || nickname.length() > MAX_NICK_NAME_LENGTH) {
            throw new InvalidNicknameException(String.format("이름은 1자 이상 1자 %d 이하여야 합니다.", MAX_NICK_NAME_LENGTH));
        }
    }

    private void validatePersonality(final List<PersonalityType> personality) {
        if(personality.isEmpty() || personality.size() > MAX_PERSONALITY_COUNT) {
            throw new InvalidPersonalityException(String.format("성격은 최대 %d개 입니다.", MAX_PERSONALITY_COUNT));
        }
    }

    public Member getMember() {
        return member;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public List<PersonalityType> getPersonality() {
        return personality;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getMainImg() {
        return mainImg;
    }

    public List<String> getSubImg() {
        return subImg;
    }

    public String getJob() {
        return job;
    }

    public AddressCity getAddressCity() {
        return addressCity;
    }

    public AddressDistrict getAddressDistrict() {
        return addressDistrict;
    }

    public int getBan() {
        return ban;
    }

    public int getJobVisible() {
        return jobVisible;
    }

    public int getSubImgVisible() {
        return subImgVisible;
    }

    public int getAddressVisible() {
        return addressVisible;
    }

    public void updateNickname(final String nickname) {
        validateNickName(nickname);
        this.nickname = nickname;
    }

    public void updateAge(final int age) {
        this.age = age;
    }

    public void updateGender(final int gender) {
        this.gender = gender;
    }

    public void updatePersonality(final List<PersonalityType> personality) {
        validatePersonality(personality);
        this.personality = personality;
    }

    public void updateIntroduce(final String introduce) {
        this.introduce = introduce;
    }

    public void updateMainImg(final String mainImg) {
        this.mainImg = mainImg;
    }

    public void updateSubImg(final List<String> subImg) {
        this.subImg = subImg;
    }

    public void updateJob(final String job) {
        this.job = job;
    }

    public void updateAddressCity(final AddressCity addressCity) {
        this.addressCity = addressCity;
    }

    public void updateAddressDistinct(final AddressDistrict addressDistrict) {
        this.addressDistrict = addressDistrict;
    }
    public void updateJobVisible(final int jobVisible) {
        this.jobVisible = jobVisible;
    }
    public void updateSubImgVisible(final int subImgVisible) {
        this.subImgVisible = subImgVisible;
    }
    public void updateAddressVisible(final int addressVisible) {
        this.addressVisible = addressVisible;
    }
}
