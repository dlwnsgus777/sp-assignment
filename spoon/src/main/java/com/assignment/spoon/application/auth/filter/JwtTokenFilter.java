package com.assignment.spoon.application.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
   private final JwtTokenProvider jwtTokenProvider;
   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String token = jwtTokenProvider.extractToken(request);
      try {
         if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
         }
      } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException |
               SignatureException | InsufficientAuthenticationException e) {
         logError(token, e);
         request.setAttribute("message", "유효하지 않은 토큰입니다.");
         request.setAttribute("code", HttpStatus.UNAUTHORIZED.value());
         request.setCharacterEncoding("UTF-8");
      } catch (ExpiredJwtException e) {
         logError(token, e);
         request.setAttribute("message", "만료된 토큰입니다.");
         request.setAttribute("code", HttpStatus.FORBIDDEN.value());
         request.setCharacterEncoding("UTF-8");
      }

      filterChain.doFilter(request, response);
   }

   private static void logError(String token, RuntimeException e) {
      log.error("================================================");
      log.error("JwtFilter - doFilterInternal() 오류발생");
      log.error("token : {}", token);
      log.error("Exception Message : {}", e.getMessage());
      log.error("Exception StackTrace : {");
      e.printStackTrace();
      log.error("}");
      log.error("================================================");
   }
}
