package com.hibit2.hibit2.profile.domain;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import com.hibit2.hibit2.domain.Post;

@Entity
@Getter
@Table(name = "profile_image")
public class ProfileImage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;


    @Column(name = "image_url")
    private String imageUrl;

    protected ProfileImage() {
    }
}
