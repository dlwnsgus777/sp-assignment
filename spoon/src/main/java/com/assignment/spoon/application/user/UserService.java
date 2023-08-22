package com.assignment.spoon.application.user;

import com.assignment.spoon.domain.user.UserCommand;
import com.assignment.spoon.domain.user.UserReader;
import com.assignment.spoon.domain.user.UserStore;
import com.assignment.spoon.presentation.user.UserRequest;
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

        userStore.registerUser(command);
    }
}
