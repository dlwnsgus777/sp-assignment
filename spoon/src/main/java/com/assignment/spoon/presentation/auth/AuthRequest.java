package com.assignment.spoon.presentation.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class AuthRequest {

   @Getter
   @Setter
   public static class SignIn {
      private String email;
      private String password;

      @Builder
      public SignIn(String email, String password) {
         this.email = email;
         this.password = password;
      }
   }
}
