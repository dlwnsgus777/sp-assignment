package com.assignment.spoon.auth.api;

import com.assignment.spoon.common.Scenario;
import com.assignment.spoon.presentation.auth.AuthRequest;
import com.assignment.spoon.presentation.auth.AuthResponse;
import com.assignment.spoon.presentation.user.UserRequest;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class LoginApi {
    private String email = "test@test.com";
    private String password = "password";

    public LoginApi email(String email) {
        this.email = email;
        return this;
    }

    public LoginApi password(String password) {
        this.password = password;
        return this;
    }

    public AuthResponse.SignIn request() {
        AuthRequest.SignIn request = AuthRequest.SignIn.builder()
                .email(email)
                .password(password)
                .build();

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/sign-in")
                .then()
                .log().all().extract().body().as(AuthResponse.SignIn.class);
    }
}
