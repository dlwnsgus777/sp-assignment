package com.assignment.spoon.infrastructure.user;

import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.UserReader;
import com.assignment.spoon.domain.user.fan.Fan;
import com.assignment.spoon.infrastructure.user.fan.FanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;
    private final FanRepository fanRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Override
    public Fan findFanByDjIdAndFollowerId(Long djId, Long followId) {
        return fanRepository.findByDjIdAndFollowerId(djId, followId)
              .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팬입니다."));
    }
}
