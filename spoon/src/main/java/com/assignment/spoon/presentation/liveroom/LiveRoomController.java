package com.assignment.spoon.presentation.liveroom;

import com.assignment.spoon.application.liveroom.LiveRoomService;
import com.assignment.spoon.domain.auth.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tags({
        @Tag(name = "Live Api", description = "방송 관련 api")
})
@RestController
@RequestMapping(value = "/api/live-rooms")
@RequiredArgsConstructor
public class LiveRoomController {
    private final LiveRoomService liveRoomService;

    @Operation(
            summary = "방송 시작 하기",
            description = "방송 시작 하기",
            responses = {
                    @ApiResponse(responseCode = "201", description = "방송 시작 하기 성공")
            }
    )
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void startLive(@AuthenticationPrincipal(expression = "loginUser") LoginUser loginUser) {
        liveRoomService.startLiveRoom(loginUser.getId());
    }
}
