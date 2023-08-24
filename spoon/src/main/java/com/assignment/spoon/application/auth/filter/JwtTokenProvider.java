package com.assignment.spoon.application.auth.filter;

import com.assignment.spoon.application.auth.CustomUserDetailService;
import com.assignment.spoon.domain.user.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {
   private final String secretKey;

   private final long validityInMilliseconds;
   private final CustomUserDetailService userDetailService;

   public JwtTokenProvider(
         @Value("${security.jwt.token.secret-key}") String secretKey,
         @Value("${security.jwt.token.expire-length}") long validityInMilliseconds,
         CustomUserDetailService userDetailService) {
      this.secretKey = secretKey;
      this.validityInMilliseconds = validityInMilliseconds;
      this.userDetailService = userDetailService;
   }

   public String createToken(Authentication authentication) {
      Claims claims = Jwts.claims().setSubject(authentication.getName());
      Date now = new Date();
      Date validity = new Date(now.getTime() + validityInMilliseconds);

      return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
   }

   public String createToken(UserInfo user) {
      Claims claims = Jwts.claims().setSubject(user.getEmail());
      Date now = new Date();
      Date validity = new Date(now.getTime() + validityInMilliseconds);

      return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
   }

   public boolean validateToken(String token) {
      try {
         Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
         return !claims.getBody().getExpiration().before(new Date());
      } catch (IllegalArgumentException e) {
         return false;
      }
   }

   public Authentication getAuthentication(String token) {
      UserDetails user = userDetailService.loadUserByUsername(getPayload(token));
      return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
   }

   public String extractToken(HttpServletRequest request) {
      return new JwtTokenExtractor(request).extract();
   }

   private String getPayload(String token) {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
   }
}
