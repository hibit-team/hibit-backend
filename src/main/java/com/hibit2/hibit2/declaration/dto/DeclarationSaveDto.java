package com.hibit2.hibit2.declaration.dto;


import com.hibit2.hibit2.declaration.domain.DeclarationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DeclarationSaveDto {

    @Schema(description = "신고를 작성하는 유저", example = "a")
    private String userId;
    @Schema(description = "게시글 ID (nullable)", example = "123")
    private Integer postIdx;
    @Schema(description = "댓글 ID (nullable)", example = "456")
    private Integer commentIdx;
    @Schema(description = "신고 타입", example = "COMMENT")
    private DeclarationType declarationType;
    @Schema(description = "신고 내용", example = "댓글 작성 과정에서 욕설이 포함되어 있었음")
    private String content;


}
