package org.saas.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.saas.admin.common.conversion.exception.ClientException;
import org.saas.admin.dao.entity.UserDO;
import org.saas.admin.dao.mapper.UserMapper;
import org.saas.admin.dto.res.UserRegisterReqDTO;
import org.saas.admin.dto.resp.UserRespDTO;
import org.saas.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static org.saas.admin.common.enums.UserErrorCode.USERNAME_EXIST;
import static org.saas.admin.common.enums.UserErrorCode.USER_SAVE_ERROR;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> query= Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername,username);
        UserDO userDO=baseMapper.selectOne(query);
        UserRespDTO result = new UserRespDTO();
        if (userDO!=null){
            BeanUtils.copyProperties(userDO, result);
            return result;
        }
        return null;
    }

    @Override
    public Boolean hashUsername(String username) {
       return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (hashUsername(requestParam.getUsername())){
            throw new ClientException(USERNAME_EXIST);
        }
        int inserted = baseMapper.insert(BeanUtil.toBean(requestParam,UserDO.class));
        if (inserted < 1){
            throw new ClientException(USER_SAVE_ERROR);
        }
    }

}