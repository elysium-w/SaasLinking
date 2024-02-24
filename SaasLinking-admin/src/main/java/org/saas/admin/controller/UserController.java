package org.saas.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.common.conversion.result.Results;
import org.saas.admin.common.enums.UserErrorCode;
import org.saas.admin.dto.resp.UserActuralRespDTO;
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
        return Results.success(userService.getUserByUsername(username));
    }
    @GetMapping("/api/link/v1/actual/user/{username}")
    public Result<UserActuralRespDTO> getActualUserByUsername(@PathVariable("username")String username){
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActuralRespDTO.class));
    }

}
