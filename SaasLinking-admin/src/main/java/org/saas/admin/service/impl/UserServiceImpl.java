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
import org.saas.admin.dto.req.UserLoginReqDTO;
import org.saas.admin.dto.req.UserRegisterReqDTO;
import org.saas.admin.dto.req.UserUpdateReqDTO;
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
     * @param username 用户名
     * @return 是否存在
     */
    @Override
    public Boolean hashUsername(String username) {
       return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    /**
     * 添加新用户
     * @param requestParam    注册用户请求参数
     */
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

    /**
     * 修改用户信息
     * @param requestParam UserUpdateReqDTO
     */
    @Override
    public void update(UserUpdateReqDTO requestParam) {
//        TODO 验证当前用户名是否为登录用户
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername,requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam,UserDO.class),updateWrapper);
    }

    /**
     * 用户登录
     * @param requestParam 用户登录请求参数
     * @return UserLoginRespDTO
     */
    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
//        检查用户是否存在于数据库
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername,requestParam.getUsername())
                .eq(UserDO::getPassword,requestParam.getPassword())
                .eq(UserDO::getDelFlag,0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
//        不存在用户
        if (userDO == null){
            throw new ClientException(USER_NULL);
        }
//        检查用户是否已经登陆，就是检查redis中是否存在这个用户
        Boolean hasLogin = stringRedisTemplate.hasKey("login_"+requestParam.getUsername());
        if (hasLogin != null && hasLogin){
            throw new ClientException("用户已登录");
        }
//        生成用户token
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put("login_"+requestParam.getUsername(),uuid,JSON.toJSONString(userDO));
//        设置登陆的过期时间为30天
        stringRedisTemplate.expire("login_"+requestParam.getUsername(),30L,TimeUnit.DAYS);
        return new UserLoginRespDTO(uuid);
    }

    @Override
    public Boolean checkLogin(String username,String token) {
        return stringRedisTemplate.opsForHash().get("login_"+username,token) != null;
    }

    @Override
    public void logout(String username, String token) {
        if (checkLogin(username,token)){
            stringRedisTemplate.delete("login_"+username);
        }else {
            throw new ClientException("用户未登陆，无法退出");
        }
    }


}