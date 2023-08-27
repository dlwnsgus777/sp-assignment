package com.assignment.spoon.infrastructure.user;

import com.assignment.spoon.domain.user.UserCommand;
import com.assignment.spoon.domain.user.UserStore;
import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.domain.user.fan.Fan;
import com.assignment.spoon.infrastructure.user.block.BlockHistoryRepository;
import com.assignment.spoon.infrastructure.user.fan.FanRepository;
import com.assignment.spoon.presentation.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;
    private final FanRepository fanRepository;
    private final BlockHistoryRepository blockHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserCommand.SignUp command) {
        userRepository.save(command.toEntity(passwordEncoder));
    }

    @Override
    public void registerFan(Fan fan) {
        fanRepository.save(fan);
    }

    @Override
    public void removeFan(Fan fan) {
        fanRepository.delete(fan);
    }

    @Override
    public void registerBlockUser(BlockHistory entity) {
        blockHistoryRepository.save(entity);
    }
}
