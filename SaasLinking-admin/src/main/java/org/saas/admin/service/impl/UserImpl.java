package org.saas.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.saas.admin.dao.entity.UserDao;
import org.saas.admin.dao.mapper.UserMapper;
import org.saas.admin.dto.resp.UserRespDTO;
import org.saas.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImpl extends ServiceImpl<UserMapper, UserDao> implements UserService {


    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDao> query= Wrappers.lambdaQuery(UserDao.class)
                .eq(UserDao::getUsername,username);
        UserDao user=baseMapper.selectOne(query);
        if (user==null){
            System.out.println("error");
        }
        UserRespDTO result=new UserRespDTO();
        BeanUtils.copyProperties(user,result);
        return result;
    }
}