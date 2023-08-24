package com.assignment.spoon.presentation.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AuthResponse {

   @Getter
   @Setter
   @AllArgsConstructor
   public static class SignIn {
      private String token;
   }
}
