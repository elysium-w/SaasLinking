package org.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.admin.dao.entity.UserDao;
import org.saas.admin.dto.resp.UserRespDTO;

public interface UserService extends IService<UserDao> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);
}
