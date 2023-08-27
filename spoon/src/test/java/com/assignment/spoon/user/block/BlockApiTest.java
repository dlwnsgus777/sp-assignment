package com.assignment.spoon.user.block;

import com.assignment.spoon.common.ApiTest;
import com.assignment.spoon.common.Scenario;
import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.infrastructure.user.block.BlockHistoryRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저 차단 관련 API 테스트")
public class BlockApiTest extends ApiTest {

    @Autowired
    BlockHistoryRepository blockHistoryRepository;

    @Test
    @DisplayName("유저를 차단할 수 있다.")
    @Transactional(readOnly = true)
    void blockUserTest() {
        // given
        String listenerEmail = "listener@listener.com";
        String djUserToken = Scenario.registerUser().request()
                .signIn().request().getToken();
        String listenerToken = Scenario.registerUser().email(listenerEmail).request()
                .signIn().email(listenerEmail).request().getToken();
        Scenario.startLiveRoom().request(djUserToken)
                .userFollow().request(listenerToken, 1L);


        ExtractableResponse<Response> result = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + listenerToken)
                .when()
                .post("/api/users/1/block")
                .then()
                .log().all().extract();

        BlockHistory blockHistory = blockHistoryRepository.findByBlockUserPk_UserIdAndBlockUserPk_BlockUserId(
                2L,
                1L
        ).orElseThrow(() -> new IllegalArgumentException("테스트 실패"));

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(blockHistory.getBlockUserPk().getBlockUserId()).isEqualTo(1L);
        assertThat(blockHistory.getBlockUserPk().getUserId()).isEqualTo(2L);
    }
}
