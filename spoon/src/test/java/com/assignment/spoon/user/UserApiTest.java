package com.assignment.spoon.user;

import com.assignment.spoon.common.ApiTest;
import com.assignment.spoon.common.Scenario;
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
        String email = "test@test.com";

        Scenario.registerUser().email(email).request();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("저장실패"));

        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("회원을 등록 요청시 이메일 형식이 맞지 않으면 오류가 발생한다.")
    void signUpUserFailTest() {
        UserRequest.SignUp request = UserRequest.SignUp.builder()
                .email("testaszxczxcccczsc")
                .password("password")
                .build();

        ExtractableResponse<Response> result = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/users/sign-up")
                .then()
                .log().all().extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}