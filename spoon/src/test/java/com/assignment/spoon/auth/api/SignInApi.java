package com.assignment.spoon.auth.api;

import com.assignment.spoon.presentation.auth.AuthRequest;
import com.assignment.spoon.presentation.auth.AuthResponse;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class SignInApi {
    private String email = "test@test.com";
    private String password = "password";

    public SignInApi email(String email) {
        this.email = email;
        return this;
    }

    public SignInApi password(String password) {
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
