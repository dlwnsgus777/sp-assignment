package com.assignment.spoon.presentation.user;

import com.assignment.spoon.application.user.UserService;
import com.assignment.spoon.domain.auth.LoginUser;
import com.assignment.spoon.domain.user.UserCommand;
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
    public void followUser(
            @PathVariable("userId") Long userId,
            @AuthenticationPrincipal(expression = "loginUser") LoginUser loginUser) {

        userService.followUser(UserCommand.FollowUser
                .builder()
                .djUserId(userId)
                .listenerId(loginUser.getId())
                .build()
        );
    }

    @PostMapping("/users/{userId}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void blockUser(
          @PathVariable("userId") Long userId,
          @AuthenticationPrincipal(expression = "loginUser") LoginUser loginUser) {

        userService.blockUser(UserCommand.BlockUser
              .builder()
              .blockUserId(userId)
              .requestUserId(loginUser.getId())
              .build()
        );
    }
}
