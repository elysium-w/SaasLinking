package org.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.admin.dao.entity.UserDO;
import org.saas.admin.dto.res.UserLoginReqDTO;
import org.saas.admin.dto.res.UserRegisterReqDTO;
import org.saas.admin.dto.res.UserUpdateReqDTO;
import org.saas.admin.dto.resp.UserLoginRespDTO;
import org.saas.admin.dto.resp.UserRespDTO;

public interface UserService extends IService<UserDO> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 校验用户名是否已经存在
     * @param username   用户名
     * @return           用户返回实体
     */
    Boolean hashUsername(String username);

    /**
     * 用户注册功能
     * @param requestParam    注册用户请求参数
     */
    void register(UserRegisterReqDTO requestParam);
    /**
     * 修改信息功能
     */
    void update(UserUpdateReqDTO requestParam);
    /**
     * 用户登录
     *
     * @param requestParam 用户登录请求参数
     * @return 用户登录返回参数 Token
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 检查用户是否登录
     * @param username  用户
     * @param token     用户登录 Token
     * @return          用户是否登录标识
     */
    Boolean checkLogin(String username,String token);
}
