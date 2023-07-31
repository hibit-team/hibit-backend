package com.hibit2.hibit2.auth.event;

public class MemberSavedEvent {

    private final Long memberId;

    public MemberSavedEvent(final Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
