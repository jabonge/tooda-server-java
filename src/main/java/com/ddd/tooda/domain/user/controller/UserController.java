package com.ddd.tooda.domain.user.controller;

import com.ddd.tooda.common.LoginUserId;
import com.ddd.tooda.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserController.USER)
@RequiredArgsConstructor
public class UserController {

    public static final String USER = "/user";
    private final UserService userService;

    @DeleteMapping
    @ResponseBody
    ResponseEntity<Void> deleteUser(@LoginUserId Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
