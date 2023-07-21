package com.hibit2.hibit2.declaration.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeclarationType {

    PRIVATE("개인 연락처 또는 1:1 만남 강요"),
    PURPOSE("히빗 취지에 반하는 만남 유도"),
    THREATS("비방, 비하, 폭언, 위협"),
    SEXUALLY("성적 불쾌감 유발하는 언어 사용"),
    RELIGION("특정 종교의 권유 및 포교"),
    SUSPECTED("사칭 및 사기 의심"),
    COMMERCIAL("상업적 광고 및 판매 유도"),
    POLITICAL("과도한 정치적 견해 표출"),
    ETC("기타");

    private final String decs;

}
