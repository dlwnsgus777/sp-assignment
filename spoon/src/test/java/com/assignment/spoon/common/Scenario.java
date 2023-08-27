package com.assignment.spoon.common;

import com.assignment.spoon.auth.api.SignInApi;
import com.assignment.spoon.liveroom.api.StartLiveRoomApi;
import com.assignment.spoon.user.api.FollowApi;
import com.assignment.spoon.user.api.RegisterUserApi;

public class Scenario {
    public static RegisterUserApi registerUser() {
        return new RegisterUserApi();
    }

    public static SignInApi signIn() {
        return new SignInApi();
    }

    public static StartLiveRoomApi startLiveRoom() {
        return new StartLiveRoomApi();
    }

    public static FollowApi userFollow() {
        return new FollowApi();
    }
}
