package com.assignment.spoon.presentation;

import com.assignment.spoon.common.ApiTest;
import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.infrastructure.user.UserRepository;
import com.assignment.spoon.presentation.user.UserRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

class UserApiTest extends ApiTest {

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("회원을 등록한다.")
    void signUpUserTest() {
        UserRequest.SignUp request = UserRequest.SignUp.builder()
                .email("test@test.com")
                .password("password")
                .build();

        ExtractableResponse<Response> result = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/users/sign-up")
                .then()
                .log().all().extract();

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("저장실패"));

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(user.getEmail()).isEqualTo(request.getEmail());
        assertThat(user.getPassword()).isNotEqualTo(request.getPassword());
    }
}