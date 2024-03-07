package org.saas.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.common.conversion.result.Results;
import org.saas.admin.dto.res.UserLoginReqDTO;
import org.saas.admin.dto.res.UserRegisterReqDTO;
import org.saas.admin.dto.res.UserUpdateReqDTO;
import org.saas.admin.dto.resp.UserActuralRespDTO;
import org.saas.admin.dto.resp.UserLoginRespDTO;
import org.saas.admin.dto.resp.UserRespDTO;
import org.saas.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/api/link/v1/user/has-username")
    public Result<Boolean> hashUsername(@RequestParam("username")String username){
        return Results.success(!userService.hashUsername(username));
    }
    /**
     * 注册用户
     */
    @PostMapping("/api/link/v1/user/")
    public Result<Void> userRegister(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();
    }
    /**
     * 修改用户信息
     */
    @PutMapping("/api/link/v1/user/")
    public Result<Void> userUpdate(@RequestBody UserUpdateReqDTO requestParam){
        userService.update(requestParam);
        return Results.success();
    }

    /**
     *  用户登录信息
     */
    @PostMapping("/api/link/v1/user/login")
    public Result<UserLoginRespDTO> userLogin(@RequestBody UserLoginReqDTO requestParam){
        return Results.success(userService.login(requestParam));
    }
    @GetMapping("/api/link/v1/user/check-login")
    public Result<Boolean> checkLogin (@RequestParam("username") String username, @RequestParam("token")String token){
        return  Results.success(userService.checkLogin(username,token));
    }

    /**
     * 用户退出登录
     * @return
     */
    @DeleteMapping("/api/link/v1/user/logout")
    public Result<Void> logout(){
        return Results.success();
    }

}
