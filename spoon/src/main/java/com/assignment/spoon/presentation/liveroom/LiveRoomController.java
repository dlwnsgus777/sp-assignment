package com.assignment.spoon.presentation.liveroom;

import com.assignment.spoon.application.liveroom.LiveRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/live-rooms")
@RequiredArgsConstructor
public class LiveRoomController {
    private final LiveRoomService liveRoomService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void startLive(@RequestBody LiveRoomRequest.StartLive request) {
        liveRoomService.startLiveRoom(request.getUserId());
    }
}
