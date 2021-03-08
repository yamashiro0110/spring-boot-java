package org.yamashiro0110.sample.auth;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Tokenで認証を行うFilter
 * <p>
 * 認証出来ないリクエストはエラーを発生させる。
 */
public class TokenAuthFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(TokenAuthFilter.class);
    private final AuthenticationManager authenticationManager;

    protected TokenAuthFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Bearer Tokenを取得する
     *
     * @param request HTTPリクエスト
     * @return Tokenの値
     */
    private String bearerToken(final HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String removeDirective = StringUtils.removeStartIgnoreCase(authHeader, "Bearer ");
        return StringUtils.removeEnd(removeDirective, ";");
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        try {
            final String token = this.bearerToken(request);
            this.logger.info("token: {}", token);

            if (StringUtils.isBlank(token)) {
                throw new PreAuthenticatedCredentialsNotFoundException("Bearerトークンが指定されていません");
            }

            // authenticationManager.authenticateの引数を作成
            final PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(token, null);

            /*
             * tokenで認証を実行する。
             *
             * ApiAuthConfig.configure(HttpSecurity.authenticationProvider)で
             * TokenAuthProviderを指定するとProviderManagerに追加される。
             *
             * よって、TokenAuthProvider.authenticateが実行される。
             */
            final Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

            // 認証の結果をセットすると、認証済みになる
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (final AuthenticationException e) {
            this.logger.error("認可エラーです", e);
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "許可出来ないリクエストです");
        }
    }
}
