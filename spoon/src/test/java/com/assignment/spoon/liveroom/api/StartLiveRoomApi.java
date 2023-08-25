package com.assignment.spoon.liveroom.api;

import com.assignment.spoon.common.Scenario;
import com.assignment.spoon.presentation.user.UserRequest;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class StartLiveRoomApi {
    public Scenario request(String token) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/live-rooms")
                .then()
                .log().all().extract();

        return new Scenario();
    }
}
