package com.assignment.spoon.application.auth;

import com.assignment.spoon.application.auth.filter.JwtTokenProvider;
import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.UserInfo;
import com.assignment.spoon.domain.user.UserReader;
import com.assignment.spoon.presentation.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
   private final JwtTokenProvider jwtTokenProvider;
   private final PasswordEncoder passwordEncoder;
   private final UserReader userReader;


   @Transactional(readOnly = true)
   public AuthResponse.SignIn signIn(String email, String password) {
      User user = userReader.findByEmail(email);
      if (!passwordEncoder.matches(password, user.getPassword())) {
         throw new IllegalArgumentException("로그인에 실패했습니다.");
      }

      String jwtToken = jwtTokenProvider.createToken(UserInfo.builder().id(user.getId()).email(user.getEmail()).build());
      return new AuthResponse.SignIn(jwtToken);
   }
}
