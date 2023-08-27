package com.hibit2.hibit2.declaration.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.comment.domain.Comment;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.user.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "declaration")
public class Declaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "report_idx")
    private Member report;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "post_idx")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "comment_idx")
    private Comment comment;

    @Schema(description = "알림 타입", example = "COMMENT")
    @Enumerated(EnumType.STRING)
    private DeclarationType declarationType;

    @Column(length = 500,nullable = false)
    @Schema(description = "신고 내용", example = "댓글 작성 과정에서 욕설이 포함되어 있었음")
    private String content;


    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    @Schema(description = "관리자 확인 여부", example = "True")
    private boolean readed;

    public void changeRead() {this.readed = true;}


}
