package com.assignment.spoon.config;

import com.assignment.spoon.application.auth.exception.JwtAccessDeniedHandler;
import com.assignment.spoon.application.auth.exception.JwtAuthenticationEntryPoint;
import com.assignment.spoon.application.auth.filter.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().disable().logout().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/api/sign-in",
                      "/api/sign-up"
                ).permitAll()
              .anyRequest().authenticated();

        http
              .exceptionHandling()
              .accessDeniedHandler(jwtAccessDeniedHandler)
              .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http
              .apply(new JwtSecurityConfiguration(jwtTokenProvider));

        return http.build();
    }
}
