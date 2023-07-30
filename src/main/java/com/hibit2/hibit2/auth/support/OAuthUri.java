package com.hibit2.hibit2.auth.support;



@FunctionalInterface
public interface OAuthUri {
    String generate(final String redirectUri);
}
