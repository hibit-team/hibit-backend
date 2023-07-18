package com.hibit2.hibit2.file.domain;

import com.hibit2.hibit2.post.domain.Post;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Entity
@Table(name = "post_image")
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_url")
    private String imageUrl;

}
