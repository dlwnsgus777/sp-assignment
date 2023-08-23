package com.assignment.spoon.infrastructure.liveroom;

import com.assignment.spoon.domain.liveroom.LiveRoomStore;
import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LiveRoomStoreImpl implements LiveRoomStore {
    private final UserRepository userRepository;

    @Override
    public void startLiveRoom(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        user.changeStatus();

        // 라이브룸 생성 로직......
    }
}
