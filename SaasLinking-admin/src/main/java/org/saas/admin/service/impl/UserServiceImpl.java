package org.saas.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.saas.admin.common.conversion.exception.ClientException;
import org.saas.admin.common.enums.UserErrorCode;
import org.saas.admin.dao.entity.UserDO;
import org.saas.admin.dao.mapper.UserMapper;
import org.saas.admin.dto.res.UserLoginReqDTO;
import org.saas.admin.dto.res.UserRegisterReqDTO;
import org.saas.admin.dto.res.UserUpdateReqDTO;
import org.saas.admin.dto.resp.UserLoginRespDTO;
import org.saas.admin.dto.resp.UserRespDTO;
import org.saas.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.saas.admin.common.conversion.exception.ServiceException;
import java.util.DuplicateFormatFlagsException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.saas.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static org.saas.admin.common.enums.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> query= Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername,username);
        UserDO userDO=baseMapper.selectOne(query);
        if (userDO == null){
            throw new ServiceException(UserErrorCode.USER_NULL);
        }
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    /**
     * 含有返回false，不含有返回true
     * @param username
     * @return
     */
    @Override
    public Boolean hashUsername(String username) {
       return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (!hashUsername(requestParam.getUsername())){
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try{
            if (lock.tryLock()){
                try{
                    int inserted = baseMapper.insert(BeanUtil.toBean(requestParam,UserDO.class));
                    if (inserted < 1){
                        throw new ClientException(USER_SAVE_ERROR);
                    }
                }catch (DuplicateFormatFlagsException duplicateFormatFlagsException){
                    throw new ClientException(USER_EXIST);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(USER_NAME_EXIST);
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDTO requestParam) {
//        TODO 验证当前用户名是否为登录用户
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername,requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam,UserDO.class),updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername,requestParam.getUsername())
                .eq(UserDO::getPassword,requestParam.getPassword())
                .eq(UserDO::getDelFlag,0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null){
            throw new ClientException(USER_NULL);
        }
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(uuid, JSON.toJSONString(userDO),30L, TimeUnit.MINUTES);
        return new UserLoginRespDTO(uuid);
    }

    @Override
    public Boolean checkLogin(String token) {
        return stringRedisTemplate.hasKey(token);
    }

}