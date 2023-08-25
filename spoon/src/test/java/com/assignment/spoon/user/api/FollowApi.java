package com.assignment.spoon.user.api;

import com.assignment.spoon.common.Scenario;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class FollowApi {
    public Scenario request(String requestUserToken, Long djId) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + requestUserToken)
                .when()
                .post("/api/users/" + djId + "/follow")
                .then()
                .log().all().extract();

        return new Scenario();
    }

}
