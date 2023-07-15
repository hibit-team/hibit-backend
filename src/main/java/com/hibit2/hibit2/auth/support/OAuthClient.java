package com.hibit2.hibit2.auth.support;

import com.hibit2.hibit2.auth.dto.OAuthMember;

@FunctionalInterface
public interface OAuthClient {
    OAuthMember getOAuthMember(final String code);
}
