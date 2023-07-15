package com.hibit2.hibit2.profile.domain;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProfileImage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private int idx; // 프로필_이미지_IDX

    @Column(name = "image_url")
    private String imageUrl;    // 이미지_URL

    protected ProfileImage() {
    }

    @Builder
    public ProfileImage(int idx, String imageUrl) {
        this.idx = idx;
        this.imageUrl = imageUrl;
    }
}
