package com.assignment.spoon.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    @Test
    @DisplayName("회원을 등록한다.")
    void signUpUserTest() {
        UserRequest.SignUp request = new UserRequest.SignUp();

    }

    public class UserRequest {

        public class SignUp {
            private final String email;
            private final String password;
        }
    }
}