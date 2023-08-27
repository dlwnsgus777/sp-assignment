package com.assignment.spoon.application.user;

import com.assignment.spoon.domain.auth.LoginUser;
import com.assignment.spoon.domain.user.*;
import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.domain.user.fan.Fan;
import com.assignment.spoon.presentation.user.UserResponse;
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

        User requestUser = userReader.getUserById(command.getRequestUserId());

        if (requestUser.getStatus().equals(User.Status.DJ)) {
            userReader.findFanByFollowerId(command.getRequestUserId(), command.getBlockUserId())
                    .ifPresent(userStore::removeFan);
        } else {
            userReader.findFanByFollowerId(command.getBlockUserId(), command.getRequestUserId())
                    .ifPresent(userStore::removeFan);
        }

        userStore.registerBlockUser(command.toEntity());
    }

    private boolean checkExistsBlock(Long requestUserId, Long blockUserId) {
        Optional<BlockHistory> blockUser = userReader.findBlockUser(requestUserId, blockUserId);
        return blockUser.isEmpty();
    }

    public UserResponse.RetrieveUser retrieveUser(Long userId, LoginUser loginUser) {
        checkBlockedRelationship(userId, loginUser.getId());
        UserDto.Main userResult = userReader.getUser(userId);
        if (loginUser.getStatus().equals(User.Status.DJ) &&
            userId.equals(loginUser.getId())) {
            userResult.setFanList(userReader.getFans(userId));
        }

        return UserResponse.RetrieveUser.builder()
                .user(userResult)
                .build();
    }

    private void checkBlockedRelationship(Long requestUser, Long targetUser) {
        if (!checkExistsBlock(requestUser, targetUser) ||
                !checkExistsBlock(targetUser, requestUser)
        ) {
            throw new IllegalArgumentException("차단된 관계에서는 프로필 조회를 할 수 없습니다.");
        }
    }
}
