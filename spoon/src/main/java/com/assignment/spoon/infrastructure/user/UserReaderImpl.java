package com.assignment.spoon.infrastructure.user;

import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.UserReader;
import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.domain.user.fan.Fan;
import com.assignment.spoon.infrastructure.user.block.BlockHistoryRepository;
import com.assignment.spoon.infrastructure.user.fan.FanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;
    private final FanRepository fanRepository;
    private final BlockHistoryRepository blockHistoryRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Override
    public Fan getFanByFollowerId(Long djId, Long followerId) {
        return fanRepository.findByDjIdAndFollowerId(djId, followerId)
              .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팬입니다."));
    }

    @Override
    public Optional<Fan> findFanByFollowerId(Long djId, Long followerId) {
        return fanRepository.findByDjIdAndFollowerId(djId, followerId);
    }

    @Override
    public BlockHistory getBlockUser(Long requestUserId, Long blockUserId) {
        return null;
    }

    @Override
    public Optional<BlockHistory> findBlockUser(Long requestUserId, Long blockUserId) {
        return blockHistoryRepository.findByBlockUserPk_UserIdAndBlockUserPk_BlockUserId(requestUserId, blockUserId);
    }
}
