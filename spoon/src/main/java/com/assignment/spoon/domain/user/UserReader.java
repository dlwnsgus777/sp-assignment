package com.assignment.spoon.domain.user;

public interface UserReader {

    User findByEmail(String email);
}
