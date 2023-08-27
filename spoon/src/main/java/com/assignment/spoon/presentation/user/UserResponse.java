package com.assignment.spoon.presentation.user;

import com.assignment.spoon.domain.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RetrieveUser {
        private UserDto.Main user;

        @Builder
        public RetrieveUser(UserDto.Main user) {
            this.user = user;
        }
    }
}
