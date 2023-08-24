package com.assignment.spoon.application.auth;

import com.assignment.spoon.domain.auth.LoginUser;
import com.assignment.spoon.domain.auth.LoginUserAdapter;
import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserReader UserReader;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserReader.findByEmail(username);
        LoginUser loginUser = LoginUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();

        return new LoginUserAdapter(loginUser, user.getPassword());
    }
}
