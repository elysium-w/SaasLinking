package org.saas.admin.controller;

import lombok.RequiredArgsConstructor;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.common.enums.UserErrorCode;
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
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username")String username){
        UserRespDTO result=userService.getUserByUsername(username);
        if (result==null){
            return new Result<UserRespDTO>().setCode(UserErrorCode.USER_NULL.code()).setMessage(UserErrorCode.USER_NULL.message());
        }else {
            return new Result<UserRespDTO>().setCode("0").setData(result);
        }
    }
}
