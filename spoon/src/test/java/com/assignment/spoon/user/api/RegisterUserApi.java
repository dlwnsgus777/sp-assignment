package com.assignment.spoon.user.api;

import com.assignment.spoon.common.Scenario;
import com.assignment.spoon.presentation.user.UserRequest;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class RegisterUserApi {
    private String email = "test@test.com";
    private String password = "password";

    public RegisterUserApi email(String email) {
        this.email = email;
        return this;
    }

    public RegisterUserApi password(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Scenario request() {
        UserRequest.SignUp request = UserRequest.SignUp.builder()
                .email(email)
                .password(password)
                .build();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/sign-up")
                .then()
                .log().all().extract();

        return new Scenario();
    }
}
