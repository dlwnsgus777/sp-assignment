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
        if (checkExistsEmail(command.getEmail())) {
            userStore.registerUser(command);
            return;
        }

         throw new IllegalArgumentException("이미 등록된 이메일입니다.");
    }

    public boolean checkExistsEmail(String email) {
        try {
            userReader.findByEmail(email);
        } catch (IllegalArgumentException e) {
            return true;
        }

        return false;
    }
}
