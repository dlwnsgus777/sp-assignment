package com.assignment.spoon.presentation.user;

import com.assignment.spoon.application.user.UserService;
import com.assignment.spoon.domain.auth.LoginUser;
import com.assignment.spoon.domain.user.UserCommand;
import com.assignment.spoon.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid UserRequest.SignUp request) {
        UserCommand.SignUp userCommand = UserCommand.SignUp.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        userService.signUp(userCommand);
    }

    @PostMapping("/users/{userId}/follow")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userFollow(
            @PathVariable("userId") Long userId,
            @AuthenticationPrincipal(expression = "loginUser") LoginUser loginUser) {

        userService.userFollow(UserCommand.UserFollow
                .builder()
                .djUserId(userId)
                .listenerId(loginUser.getId())
                .build()
        );
    }
}
