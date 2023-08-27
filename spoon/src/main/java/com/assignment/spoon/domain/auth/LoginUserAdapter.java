package com.assignment.spoon.domain.auth;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;

public class LoginUserAdapter extends User {
    private final LoginUser loginUser;

    public LoginUserAdapter(LoginUser loginUser, String password) {
        super(loginUser.getEmail(), password, authorities());
        this.loginUser = loginUser;
    }

    private static Collection<? extends GrantedAuthority> authorities() {
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(() -> "USER");
        return grantedAuthorities;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }
}
