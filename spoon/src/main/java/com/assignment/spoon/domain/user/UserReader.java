package com.assignment.spoon.domain.user;

public interface UserReader {
    void existsEmail(String email);

    User findByEmail(String email);
}
