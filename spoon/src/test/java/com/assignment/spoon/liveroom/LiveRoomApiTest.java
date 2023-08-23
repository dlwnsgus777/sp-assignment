package com.assignment.spoon.liveroom;

import com.assignment.spoon.common.ApiTest;
import com.assignment.spoon.common.Scenario;
import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.infrastructure.user.UserRepository;
import com.assignment.spoon.presentation.liveroom.LiveRoomRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LiveRoomApiTest extends ApiTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("방송을 시작하면 User는 DJ가 된다.")
    void startLiveRoomTest() {
        Scenario.registerUser().request();

        LiveRoomRequest.StartLive request = new LiveRoomRequest.StartLive(1L);

        ExtractableResponse<Response> result = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/live-rooms")
                .then()
                .log().all().extract();

        User findUser = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("테스트 실패"));

        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(findUser.getStatus()).isEqualTo(User.Status.DJ);
    }
}
