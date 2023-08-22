package com.assignment.spoon.common;

import com.assignment.spoon.user.api.UserRegisterApi;

public class Scenario {
    public static UserRegisterApi registerUser() {
        return new UserRegisterApi();
    }
}
