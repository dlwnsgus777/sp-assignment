package com.assignment.spoon.application.liveroom;

import com.assignment.spoon.domain.liveroom.LiveRoomStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiveRoomService {
    private final LiveRoomStore liveRoomStore;

    @Transactional
    public void startLiveRoom(Long userId) {
        liveRoomStore.startLiveRoom(userId);
    }
}
