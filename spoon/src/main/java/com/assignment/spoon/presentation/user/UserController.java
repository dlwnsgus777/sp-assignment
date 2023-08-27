package com.assignment.spoon.presentation.user;

import com.assignment.spoon.application.user.UserService;
import com.assignment.spoon.domain.auth.LoginUser;
import com.assignment.spoon.domain.user.UserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tags({
        @Tag(name = "User Api", description = "유저 관련 api")
})
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @Operation(
            summary = "회원가입 하기",
            description = "회원가입 하기",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 하기 성공")
            }
    )
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid UserRequest.SignUp request) {
        UserCommand.SignUp userCommand = UserCommand.SignUp.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        userService.signUp(userCommand);
    }

    @Operation(
            summary = "유저 정보 조회하기",
            description = "유저 정보 조회하기",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 조회하기")
            }
    )
    @GetMapping("/users/{userId}")
    public UserResponse.RetrieveUser retrieveUser(
            @PathVariable("userId") Long userId,
            @AuthenticationPrincipal(expression = "loginUser") LoginUser loginUser) {

        return userService.retrieveUser(userId, loginUser);
    }


    @Operation(
            summary = "DJ 팔로우 하기",
            description = "DJ 팔로우 하기",
            responses = {
                    @ApiResponse(responseCode = "204", description = "DJ 팔로우 하기 성공")
            }
    )
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

    @Operation(
            summary = "유저 차단하기",
            description = "유저 차단하기",
            responses = {
                    @ApiResponse(responseCode = "204", description = "유저 차단하기 성공")
            }
    )
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
