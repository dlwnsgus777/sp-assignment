package com.assignment.spoon.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Main {
        private Long id;
        private String email;
        private User.Status status;
        private LocalDateTime createdAt;
        private Long fanCount;
        private List<Info> fanList;

        @Builder
        public Main(Long id, String email, User.Status status, LocalDateTime createdAt, Long fanCount) {
            this.id = id;
            this.email = email;
            this.status = status;
            this.createdAt = createdAt;
            this.fanCount = fanCount;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Info {
        private Long id;
        private String email;

        @Builder
        public Info(Long id, String email) {
            this.id = id;
            this.email = email;
        }
    }
}
