package com.assignment.spoon.presentation.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SignUp {
        @Email(message = "email 형식에 맞지 않습니다.")
        @NotEmpty(message = "email 값은 필수입니다.")
        private String email;

        @NotEmpty(message = "password 값은 필수입니다.")
        private String password;

        @Builder
        public SignUp(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
