package com.assignment.spoon.domain.auth;

import com.assignment.spoon.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUser {
    private final Long id;
    private final String email;
    private final User.Status status;

    @Builder
    public LoginUser(Long id, String email, User.Status status) {
        this.id = id;
        this.email = email;
        this.status = status;
    }
}
