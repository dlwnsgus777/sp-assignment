package com.assignment.spoon.presentation.liveroom;

import com.assignment.spoon.application.liveroom.LiveRoomService;
import com.assignment.spoon.domain.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/live-rooms")
@RequiredArgsConstructor
public class LiveRoomController {
    private final LiveRoomService liveRoomService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void startLive(@AuthenticationPrincipal(expression = "loginUser") LoginUser loginUser) {
        liveRoomService.startLiveRoom(loginUser.getId());
    }
}
