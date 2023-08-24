package com.assignment.spoon.presentation.user;

import com.assignment.spoon.application.user.UserService;
import com.assignment.spoon.domain.user.UserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    public void test() {
        System.out.println("TEST");
    }
}
