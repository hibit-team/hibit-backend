package com.hibit2.hibit2.infrastructure.client;

import com.hibit2.hibit2.infrastructure.dto.OAuthMember;

@FunctionalInterface
public interface OAuthClient {

    OAuthMember getOAuthMember(final String code);
}