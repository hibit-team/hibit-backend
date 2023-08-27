package com.hibit2.hibit2.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@Setter
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(nullable = false, length = 15)
    @Schema(description = "아이디", example = "abc")
    private String id;

    //추후 프로필 이미지로 수정될 사항
    @Column(length = 100)
    @Schema(description = "프로필이미지 url", example = "https://qqqq")
    private String profileImg;

    @Column(nullable = true, length = 30)
    @Schema(description = "이메일", example = "abc@naver.com")
    private String email;

    @Builder
    public Users(String id, String profileImg, String email){
        this.id=id;
        this.profileImg = profileImg;
        this.email=email;
    }
}
