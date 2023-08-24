package com.assignment.spoon.presentation.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthResponse {

   @Getter
   @Setter
   public static class SignIn {
      private String token;

      public SignIn() {
      }

      public SignIn(String token) {
         this.token = token;
      }
   }
}
