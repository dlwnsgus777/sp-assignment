package com.assignment.spoon.application.user;

import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.UserCommand;
import com.assignment.spoon.domain.user.UserReader;
import com.assignment.spoon.domain.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStore userStore;
    private final UserReader userReader;

    @Transactional
    public void signUp(UserCommand.SignUp command) {
        if (checkExistsEmail(command.getEmail())) {
            userStore.registerUser(command);
            return;
        }

         throw new IllegalArgumentException("이미 등록된 이메일입니다.");
    }

    private boolean checkExistsEmail(String email) {
        try {
            userReader.findByEmail(email);
        } catch (IllegalArgumentException e) {
            return true;
        }

        return false;
    }

    @Transactional
    public void followUser(UserCommand.FollowUser command) {
        User djUser = userReader.findById(command.getDjUserId());
        User listener = userReader.findById(command.getListenerId());

        if (djUser.getStatus().equals(User.Status.LISTENER)) {
            throw new IllegalArgumentException("DJ만 팔로우 할 수 있습니다.");
        }

        if (!checkExistsFan(djUser.getId(), listener.getId())) {
            throw new IllegalArgumentException("이미 DJ의 팬입니다.");
        }

        userStore.registerFan(command.toEntity(djUser, listener));
    }

    private boolean checkExistsFan(Long djId, Long followId) {
        try {
            userReader.findFanByDjIdAndFollowerId(djId, followId);
        } catch (IllegalArgumentException e) {
            return true;
        }

        return false;
    }

    public void blockUser(UserCommand.BlockUser command) {

    }
}
