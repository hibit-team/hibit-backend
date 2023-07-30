package com.hibit2.hibit2.auth.presentation;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import com.hibit2.hibit2.auth.exception.EmptyAuthorizationHeaderException;
import com.hibit2.hibit2.auth.exception.InvalidTokenException;

public class AuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public static String extract(final HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorizationHeader)) {
            throw new EmptyAuthorizationHeaderException();
        }

        validateAuthorizationFormat(authorizationHeader);
        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
    }

    private static void validateAuthorizationFormat(final String authorizationHeader) {
        if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            throw new InvalidTokenException("token 형식이 잘못 되었습니다.");
        }
    }
}
