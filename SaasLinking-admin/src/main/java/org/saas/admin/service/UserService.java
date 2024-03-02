package org.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.admin.dao.entity.UserDO;
import org.saas.admin.dto.res.UserRegisterReqDTO;
import org.saas.admin.dto.res.UserUpdateReqDTO;
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
     * @param username
     * @return
     */
    Boolean hashUsername(String username);

    /**
     * 用户注册功能
     * @param requestParam
     */
    void register(UserRegisterReqDTO requestParam);
    /**
     * 修改信息功能
     */
    void update(UserUpdateReqDTO requestParam);
}
