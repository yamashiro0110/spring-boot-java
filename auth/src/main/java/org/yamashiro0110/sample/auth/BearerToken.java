package org.yamashiro0110.sample.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public class BearerToken extends AbstractAuthenticationToken {

    public BearerToken(final User user) {
        super(user.getAuthorities());
        this.setAuthenticated(true);
        this.setDetails(user);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.getDetails();
    }
}
