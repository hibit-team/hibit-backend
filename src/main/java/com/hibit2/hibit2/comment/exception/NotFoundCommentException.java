package com.hibit2.hibit2.comment.exception;

public class NotFoundCommentException  extends RuntimeException {
    public NotFoundCommentException(final String message) {
        super(message);
    }

    public NotFoundCommentException() {
        this("댓글을 찾을 수 없습니다.");
    }
}
