package com.assignment.spoon.config;


import com.assignment.spoon.application.auth.filter.JwtTokenFilter;
import com.assignment.spoon.application.auth.filter.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
   private final JwtTokenProvider jwtTokenProvider;

   @Override
   public void configure(HttpSecurity http) {
      JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
      http.addFilterBefore(jwtTokenFilter, ExceptionTranslationFilter.class);
   }
}
