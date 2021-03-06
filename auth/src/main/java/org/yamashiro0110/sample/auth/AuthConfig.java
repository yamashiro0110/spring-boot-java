package org.yamashiro0110.sample.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthProvider authProvider;

    AuthTokenFilter sampleAuthTokenFilter() throws Exception {
        return new AuthTokenFilter(this.authenticationManager());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
            .csrf().disable()
            .authenticationProvider(this.authProvider)
            .addFilterBefore(this.sampleAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests(authorize -> authorize
                .antMatchers(HttpMethod.GET, "/api/sample").hasAuthority("sample:get")
                .antMatchers(HttpMethod.POST, "/api/sample").hasAuthority("sample:post")
            )
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }
}
