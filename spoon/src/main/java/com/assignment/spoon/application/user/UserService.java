package com.assignment.spoon.application.user;

import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.UserCommand;
import com.assignment.spoon.domain.user.UserReader;
import com.assignment.spoon.domain.user.UserStore;
import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.domain.user.fan.Fan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<User> findUser = userReader.findUserByEmail(email);
        return findUser.isEmpty();
    }

    @Transactional
    public void followUser(UserCommand.FollowUser command) {
        User djUser = userReader.getUserById(command.getDjUserId());
        User listener = userReader.getUserById(command.getListenerId());

        if (djUser.getStatus().equals(User.Status.LISTENER)) {
            throw new IllegalArgumentException("DJ만 팔로우 할 수 있습니다.");
        }

        if (!checkExistsFan(djUser.getId(), listener.getId())) {
            throw new IllegalArgumentException("이미 DJ의 팬입니다.");
        }

        userStore.registerFan(command.toEntity(djUser, listener));
    }

    private boolean checkExistsFan(Long djId, Long followerId) {
        Optional<Fan> fan = userReader.findFanByFollowerId(djId, followerId);
        return fan.isEmpty();
    }

    @Transactional
    public void blockUser(UserCommand.BlockUser command) {
        if (!checkExistsBlock(command.getRequestUserId(), command.getBlockUserId())) {
            throw new IllegalArgumentException("이미 차단한 유저입니다.");
        }

        userReader.findFanByFollowerId(command.getRequestUserId(), command.getBlockUserId())
                .ifPresent(userStore::removeFan);

        userStore.registerBlockUser(command.toEntity());
    }

    private boolean checkExistsBlock(Long requestUserId, Long blockUserId) {
        Optional<BlockHistory> blockUser = userReader.findBlockUser(requestUserId, blockUserId);
        return blockUser.isEmpty();
    }
}
