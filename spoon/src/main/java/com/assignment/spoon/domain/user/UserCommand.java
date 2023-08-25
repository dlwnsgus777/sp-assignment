package com.assignment.spoon.domain.user;

import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.domain.user.fan.Fan;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserCommand {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BlockUser {
        private Long requestUserId;
        private Long blockUserId;

        @Builder
        public BlockUser(Long requestUserId, Long blockUserId) {
            this.requestUserId = requestUserId;
            this.blockUserId = blockUserId;
        }

        public BlockHistory toEntity() {
            return BlockHistory.builder()
                    .requestUserId(requestUserId)
                    .blockUserId(blockUserId)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FollowUser {
        private Long djUserId;
        private Long listenerId;

        @Builder
        public FollowUser(Long djUserId, Long listenerId) {
            this.djUserId = djUserId;
            this.listenerId = listenerId;
        }

        public Fan toEntity(User djUser, User listener) {
            return Fan.builder()
                    .dj(djUser)
                    .follower(listener)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SignUp {
        @Email(message = "email 형식에 맞지 않습니다.")
        @NotEmpty(message = "email 값은 필수입니다.")
        private String email;

        @NotEmpty(message = "password 값은 필수입니다.")
        private String password;

        @Builder
        public SignUp(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public User toEntity(PasswordEncoder passwordEncoder) {
            return User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .status(User.Status.LISTENER)
                    .build();
        }
    }
}
