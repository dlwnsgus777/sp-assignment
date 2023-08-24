package com.assignment.spoon.presentation.auth;

import com.assignment.spoon.application.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
   private final AuthService authService;

   @PostMapping("/api/sign-in")
   public AuthResponse.SignIn signIn(@RequestBody AuthRequest.SignIn request) {

      return authService.signIn(request.getEmail(), request.getPassword());
   }
}
