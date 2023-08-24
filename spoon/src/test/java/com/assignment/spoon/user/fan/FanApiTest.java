package com.assignment.spoon.user.fan;

import com.assignment.spoon.common.ApiTest;
import com.assignment.spoon.common.Scenario;
import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.fan.Fan;
import com.assignment.spoon.infrastructure.user.UserRepository;
import com.assignment.spoon.infrastructure.user.fan.FanRepository;
import com.assignment.spoon.presentation.user.UserRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FanApiTest extends ApiTest {
    @Autowired
    FanRepository fanRepository;


    @Test
    @DisplayName("DJ를 팔로우 할 수 있다.")
    void signUpUserTest() {
        String djUserToken = Scenario.registerUser().request()
                .signIn().request().getToken();
        String listenerToken = Scenario.registerUser().email("listener@listener.com").request()
                .signIn().request().getToken();

        Scenario.startLiveRoom().request(djUserToken);

        ExtractableResponse<Response> result = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + listenerToken)
                .when()
                .post("/api/users/1/follow")
                .then()
                .log().all().extract();


        List<Fan> fans = fanRepository.findByDjId(1L);
        assertThat(fans.size()).isNotEqualTo(0);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
