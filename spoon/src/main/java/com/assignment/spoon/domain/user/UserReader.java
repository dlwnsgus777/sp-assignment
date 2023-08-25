package com.assignment.spoon.domain.user;

import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.domain.user.fan.Fan;

import java.util.Optional;

public interface UserReader {

    User getUserByEmail(String email);

    Optional<User> findUserByEmail(String email);

    User getUserById(Long userId);

    Fan getFanByFollowerId(Long djId, Long followerId);

    Optional<Fan> findFanByFollowerId(Long djId, Long followerId);

    BlockHistory getBlockUser(Long requestUserId, Long blockUserId);

    Optional<BlockHistory> findBlockUser(Long requestUserId, Long blockUserId);
}
