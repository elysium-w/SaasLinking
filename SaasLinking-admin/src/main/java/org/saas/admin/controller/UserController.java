package org.saas.admin.controller;

import lombok.RequiredArgsConstructor;
import org.saas.admin.dto.resp.UserRespDTO;
import org.saas.admin.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/api/link/v1/user/{username}")
    public UserRespDTO getUserByUsername(@PathVariable("username")String username){
        return userService.getUserByUsername(username);
    }
}
