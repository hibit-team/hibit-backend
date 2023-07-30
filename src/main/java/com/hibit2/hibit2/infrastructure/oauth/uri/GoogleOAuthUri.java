package com.hibit2.hibit2.infrastructure.oauth.uri;

import com.hibit2.hibit2.auth.support.OAuthUri;
import com.hibit2.hibit2.global.config.properties.GoogleProperties;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthUri implements OAuthUri {

    private final GoogleProperties properties;

    public GoogleOAuthUri(final GoogleProperties properties) {
        this.properties = properties;
    }

    @Override
    public String generate(final String redirectUri) {
        return properties.getOAuthEndPoint() + "?"
                + "client_id=" + properties.getClientId() + "&"
                + "redirect_uri=" + redirectUri + "&"
                + "response_type=code&"
                + "scope=" + String.join(" ", properties.getScopes()) + "&"
                + "access_type=" + properties.getAccessType();
    }
}
