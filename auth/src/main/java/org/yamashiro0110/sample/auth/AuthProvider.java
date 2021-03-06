package org.yamashiro0110.sample.auth;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthProvider implements AuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(AuthProvider.class);

    private User user() {
        return new User(
            "user",
            "user",
            true,
            true,
            true,
            true,
            AuthorityUtils.createAuthorityList("sample:get")
        );
    }

    private User admin() {
        return new User(
            "admin",
            "admin",
            true,
            true,
            true,
            true,
            AuthorityUtils.createAuthorityList("sample:get", "sample:post")
        );
    }

    private User user(final String token) {
        if (StringUtils.equals("admin", token)) {
            return this.admin();
        } else if (StringUtils.equals("user", token)) {
            return this.user();
        } else {
            return null;
        }
    }


    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final PreAuthenticatedAuthenticationToken token = (PreAuthenticatedAuthenticationToken) authentication;
        this.logger.info("start authentication token: {}", token);

        final User user = this.user(String.valueOf(token.getPrincipal()));
        this.logger.info("get user {}", user);

        if (Objects.isNull(user)) {
            throw new BadCredentialsException("無効なtokenが指定されました " + token.getPrincipal());
        }

        return new BearerToken(user);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return Objects.equals(PreAuthenticatedAuthenticationToken.class, authentication);
    }
}
