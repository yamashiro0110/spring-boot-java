package org.yamashiro0110.sample.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * リクエストの認証・認可の設定
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    TokenAuthProvider tokenAuthProvider;

    TokenAuthFilter sampleAuthTokenFilter() throws Exception {
        return new TokenAuthFilter(this.authenticationManager());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // `/api/`のPathパターンに対する設定
        http.antMatcher("/api/**")
            // csrfを無効
            .csrf().disable()
            // 認証を実行する独自の`AuthenticationProvider`を追加する
            .authenticationProvider(this.tokenAuthProvider)
            // tokenを取得して認証を実行するfilterを設定
            .addFilterBefore(this.sampleAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            // 認可の設定
            .authorizeRequests(authorize -> authorize
                .antMatchers(HttpMethod.GET, "/api/sample").hasAuthority("sample:get")
                .antMatchers(HttpMethod.POST, "/api/sample").hasAuthority("sample:post")
            )
            // Sessionを利用しない
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }
}
