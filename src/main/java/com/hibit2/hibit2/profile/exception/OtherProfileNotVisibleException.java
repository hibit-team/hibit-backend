package com.hibit2.hibit2.profile.exception;

// 사용 안함
public class OtherProfileNotVisibleException extends RuntimeException {
    public OtherProfileNotVisibleException(final String message) {
        super(message);
    }

    public OtherProfileNotVisibleException() {
        this("타인 회원의 프로필은 비공개 상태입니다.");
    }
}
