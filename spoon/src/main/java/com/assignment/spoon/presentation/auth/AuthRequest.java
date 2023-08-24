package com.assignment.spoon.presentation.auth;

import lombok.Getter;
import lombok.Setter;

public class AuthRequest {

   @Getter
   @Setter
   public static class SignIn {
      private String email;
      private String password;
   }
}
