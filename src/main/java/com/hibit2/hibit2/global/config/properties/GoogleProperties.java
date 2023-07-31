package com.hibit2.hibit2.global.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("oauth.google")
@ConstructorBinding
public class GoogleProperties {
    private final String clientId;
    private final String clientSecret;
    private final String oAuthEndPoint;
    private final String responseType;
    private final String scopes;
    private final String tokenUri;
    private final String accessType;

    public GoogleProperties(@Value("${oauth.google.client-id}") final String clientId,
                            @Value("${oauth.google.client-secret}") final String clientSecret,
                            @Value("${oauth.google.oauth-end-point}") final String oAuthEndPoint,
                            @Value("${oauth.google.response-type}") final String responseType,
                            @Value("${oauth.google.scopes}") final String scopes,
                            @Value("${oauth.google.token-uri}") final String tokenUri,
                            @Value("${oauth.google.access-type}") final String accessType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.oAuthEndPoint = oAuthEndPoint;
        this.responseType = responseType;
        this.scopes = scopes;
        this.tokenUri = tokenUri;
        this.accessType = accessType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getOAuthEndPoint() {
        return oAuthEndPoint;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getScopes() {
        return scopes;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public String getAccessType() {
        return accessType;
    }
}
