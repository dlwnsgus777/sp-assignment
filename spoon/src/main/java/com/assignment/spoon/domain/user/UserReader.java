package com.assignment.spoon.domain.user;

import com.assignment.spoon.domain.user.fan.Fan;

public interface UserReader {

    User findByEmail(String email);

    User findById(Long userId);

    Fan findFanByDjIdAndFollowerId(Long djId, Long followerId);
}
