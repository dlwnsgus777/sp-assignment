package com.assignment.spoon.user.api;

import com.assignment.spoon.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class BlockApi {

    public Scenario request(String requestUserToken, Long targetId) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + requestUserToken)
                .pathParam("userId", targetId)
                .when()
                .post("/api/users/{userId}/block")
                .then()
                .log().all().extract();

        return new Scenario();
    }
}
