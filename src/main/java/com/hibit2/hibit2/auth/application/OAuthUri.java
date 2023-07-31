package com.hibit2.hibit2.auth.application;



@FunctionalInterface
public interface OAuthUri {
    String generate(final String redirectUri);
}
