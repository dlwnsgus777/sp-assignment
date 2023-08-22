package com.assignment.spoon.infrastructure.user;

import com.assignment.spoon.domain.user.UserCommand;
import com.assignment.spoon.domain.user.UserStore;
import com.assignment.spoon.presentation.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void registerUser(UserCommand.SignUp command) {
        userRepository.save(command.toEntity(passwordEncoder));
    }
}
