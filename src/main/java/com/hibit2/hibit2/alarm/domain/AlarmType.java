package com.hibit2.hibit2.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
     COMMENT("댓글"),
     RECOMMENT("대댓글"),
     COMMENTHEART("댓글좋아요"),
     INVITATION("초대장"),
     OPENCHAT("오픈채팅방"),
     ACCEPT("수락"),
     REFUSE("거절"),
     REPORT("신고"),
     EVENT("이벤트");

    private final String decs;
}
