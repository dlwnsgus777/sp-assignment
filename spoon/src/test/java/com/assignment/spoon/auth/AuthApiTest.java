package com.assignment.spoon.auth;

import com.assignment.spoon.common.ApiTest;
import com.assignment.spoon.common.Scenario;
import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.presentation.auth.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthApiTest extends ApiTest {

   @Test
   @DisplayName("이메일과 비밀번호로 로그인한다.")
   void signInTest() {
      Scenario.registerUser().request();

      AuthRequest.SignIn request = AuthRequest.SignIn.builder()
            .email(Scenario.registerUser().getEmail())
            .password(Scenario.registerUser().getPassword())
            .build();

      ExtractableResponse<Response> result = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/api/sign-in")
            .then()
            .log().all().extract();

      assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
      assertThat(result.jsonPath().getString("token")).isNotBlank();
   }

   @Test
   @DisplayName("저장되지 않은 이메일로 로그인 시도를 하면 실패한다.")
   void signInFailTestNotFound() {
      Scenario.registerUser().request();

      AuthRequest.SignIn request = AuthRequest.SignIn.builder()
            .email("test@ttttt.aacc")
            .password(Scenario.registerUser().getPassword())
            .build();

      ExtractableResponse<Response> result = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/api/sign-in")
            .then()
            .log().all().extract();

      assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
   }

   @Test
   @DisplayName("비밀번호가 틀리면 로그인에 실패한다.")
   void signInFailTestPasswordFail() {
      Scenario.registerUser().request();

      AuthRequest.SignIn request = AuthRequest.SignIn.builder()
            .email(Scenario.registerUser().getEmail())
            .password("failPassword")
            .build();

      ExtractableResponse<Response> result = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/api/sign-in")
            .then()
            .log().all().extract();

      assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
   }
}
