package com.assignment.spoon.presentation.auth;

import com.assignment.spoon.application.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tags({
        @Tag(name = "Auth Api", description = "유저 인증 관련 api")
})
@RestController
@RequiredArgsConstructor
public class AuthController {
   private final AuthService authService;

   @Operation(
           summary = "로그인 하기",
           description = "로그인 하기",
           responses = {
                   @ApiResponse(responseCode = "200", description = "로그인 성공")
           }
   )
   @PostMapping("/api/sign-in")
   public AuthResponse.SignIn signIn(@RequestBody AuthRequest.SignIn request) {
      return authService.signIn(request.getEmail(), request.getPassword());
   }
}
