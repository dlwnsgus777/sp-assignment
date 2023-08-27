package com.assignment.spoon.user;

import com.assignment.spoon.common.ApiTest;
import com.assignment.spoon.common.Scenario;
import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.infrastructure.user.UserRepository;
import com.assignment.spoon.presentation.user.UserRequest;
import com.assignment.spoon.presentation.user.UserResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저 관련 API 테스트")
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
                .post("/api/sign-up")
                .then()
                .log().all().extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("이미 회원가입이 된 이메일이면 오류가 발생한다.")
    void signUpUserFailExistEmail() {
        String email = "test@test.com";
        Scenario.registerUser().email(email).request();

        UserRequest.SignUp request = UserRequest.SignUp.builder()
              .email("email")
              .password("password")
              .build();

        ExtractableResponse<Response> result = RestAssured.given().log().all()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .body(request)
              .when()
              .post("/api/sign-up")
              .then()
              .log().all().extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Listener가 DJ의 프로필을 조회하여 DJ의 Fan Count를 확인할 수 있다.")
    void retrieveUserTest() {
        String listenerEmail = "listener@listener.com";
        String djUserToken = Scenario.registerUser().request()
                .signIn().request().getToken();
        String listenerToken = Scenario.registerUser().email(listenerEmail).request()
                .signIn().email(listenerEmail).request().getToken();
        Scenario.startLiveRoom().request(djUserToken)
                .userFollow().request(listenerToken, 1L);

        ExtractableResponse<Response> result = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + listenerToken)
                .when()
                .get("/api/users/1")
                .then()
                .log().all().extract();

        UserResponse.RetrieveUser retrieveUser = result.body().as(UserResponse.RetrieveUser.class);

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(retrieveUser.getUser().getFanCount()).isEqualTo(1L);
    }

    @Test
    @DisplayName("차단된 관계에서는 유저 조회를 할 수 없다.")
    void retrieveUserFailTest() {
        String listenerEmail = "listener@listener.com";
        String djUserToken = Scenario.registerUser().request()
                .signIn().request().getToken();
        String listenerToken = Scenario.registerUser().email(listenerEmail).request()
                .signIn().email(listenerEmail).request().getToken();
        Scenario.startLiveRoom().request(djUserToken)
                .userFollow().request(listenerToken, 1L)
                .userBlock().request(djUserToken, 2L);

        ExtractableResponse<Response> result = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + listenerToken)
                .when()
                .get("/api/users/1")
                .then()
                .log().all().extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}