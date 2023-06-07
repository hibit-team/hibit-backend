package com.hibit2.hibit2.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;
    public Matching(Users user, Post post) {
        this.user = user;
        this.post = post;
        this.status = MatchStatus.HOLDING;
    }

    public void setUser(Users user) {this.user = user;}
    public void setPost(Post post) {this.post = post;}


}
