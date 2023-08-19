package com.hibit2.hibit2.auth.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthTokenResponseHandler {

    private final HttpServletResponse httpServletResponse;

    @Autowired
    public AuthTokenResponseHandler(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public void setRefreshTokenCookie(String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true); // HttpOnly 설정
        refreshTokenCookie.setSecure(true); // https 옵션 설정
        refreshTokenCookie.setMaxAge(14 * 24 * 60 * 60); // 리프레시 토큰 유효 기간 설정 (14일)
        refreshTokenCookie.setPath("/"); // 쿠키의 유효 경로 설정 (애플리케이션 전체)
        httpServletResponse.addCookie(refreshTokenCookie);
    }
}
