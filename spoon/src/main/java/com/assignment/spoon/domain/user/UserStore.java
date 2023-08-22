package com.assignment.spoon.domain.user;

import com.assignment.spoon.presentation.user.UserRequest;

public interface UserStore {
    void registerUser(UserCommand.SignUp command);
}
