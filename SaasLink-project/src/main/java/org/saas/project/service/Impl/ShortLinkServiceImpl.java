package org.saas.project.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.saas.project.common.conversion.exception.ClientException;
import org.saas.project.common.conversion.exception.ServiceException;
import org.saas.project.common.enums.VailDateTypeEnum;
import org.saas.project.dao.entity.ShortLinkDO;
import org.saas.project.dao.entity.ShortLinkGotoDO;
import org.saas.project.dao.mapper.ShortLinkGotoMapper;
import org.saas.project.dao.mapper.ShortLinkMapper;
import org.saas.project.dto.req.ShortLinkCreateReqDTO;
import org.saas.project.dto.req.ShortLinkPageReqDTO;
import org.saas.project.dto.req.ShortLinkUpdateReqDTO;
import org.saas.project.dto.resp.ShortLinkCreateRespDTO;
import org.saas.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.saas.project.dto.resp.ShortLinkPageRespDTO;
import org.saas.project.service.ShortLinkService;
import org.saas.project.tookit.HashUtil;
import org.saas.project.tookit.LinkUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.saas.project.common.constant.RedisKeyConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {
    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;
    private final ShortLinkGotoMapper shortLinkGotoMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;
    @Override
    public ShortLinkCreateRespDTO createLink(ShortLinkCreateReqDTO requestParam) {
        String shortLinkSuffix = generateSuffix(requestParam);
        String fullShortUrl = StrBuilder.create(requestParam.getDomain())
                .append("/")
                .append(shortLinkSuffix)
                .toString();
        ShortLinkDO linkDO = ShortLinkDO.builder()
                .domain(requestParam.getDomain())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .createdType(requestParam.getCreatedType())
                .validDate(requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .shortUri(shortLinkSuffix)
                .enableStatus(0)
                .fullShortUrl(fullShortUrl)
                .build();
        ShortLinkGotoDO shortLinkGotoDO = ShortLinkGotoDO.builder()
                .fullShortUrl(fullShortUrl)
                .gid(requestParam.getGid())
                .build();
        try{
            baseMapper.insert(linkDO);
            shortLinkGotoMapper.insert(shortLinkGotoDO);
        }catch (DuplicateKeyException ex){
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                            .eq(ShortLinkDO::getFullShortUrl,fullShortUrl);
            ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
            if (shortLinkDO != null){
                log.warn("短链接："+ fullShortUrl +"重复入库" );
                throw new ServiceException("短链接生成重复");
            }
        }
//        缓存预热，创建的时候直接加入缓存里面
        stringRedisTemplate.opsForValue().set(
                String.format(GOTO_SHORT_LINK_KEY,fullShortUrl),
                requestParam.getOriginUrl(),
                LinkUtil.getLinkCacheValidDate(requestParam.getValidDate()), TimeUnit.MILLISECONDS
        );
        shortUriCreateCachePenetrationBloomFilter.add(fullShortUrl);
        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl("http://" + linkDO.getFullShortUrl())
                .originUrl(requestParam.getOriginUrl())
                .build();
    }

    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid,requestParam.getGid())
                .eq(ShortLinkDO::getEnableStatus,0)
                .eq(ShortLinkDO::getDelFlag,0);
        IPage<ShortLinkDO> page = baseMapper.selectPage(requestParam,queryWrapper);
        return page.convert(each -> {
            ShortLinkPageRespDTO result = BeanUtil.toBean(each, ShortLinkPageRespDTO.class);
            result.setDomain("http://" + result.getDomain());
            return result;
        });
    }

    @Override
    public List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> requestParam) {
        QueryWrapper<ShortLinkDO> queryWrapper = Wrappers.query(new ShortLinkDO())
                .select("gid as gid,count(*) as shortLinkCount")
                .in("gid",requestParam)
                .eq("enable_status",0)
                .groupBy("gid");
        List<Map<String,Object>> shortLinkDOList = baseMapper.selectMaps(queryWrapper);
        return BeanUtil.copyToList(shortLinkDOList, ShortLinkGroupCountQueryRespDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShortLink(ShortLinkUpdateReqDTO requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid,requestParam.getGid())
                .eq(ShortLinkDO::getFullShortUrl,requestParam.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag,0)
                .eq(ShortLinkDO::getEnableStatus,0);
        ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
        if (hasShortLinkDO == null){
            throw new ClientException("短链接不存在");
        }
//        新构建一个对象
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .domain(hasShortLinkDO.getDomain())
                .shortUri(hasShortLinkDO.getShortUri())
                .clickNum(hasShortLinkDO.getClickNum())
                .favicon(hasShortLinkDO.getFavicon())
                .createdType(hasShortLinkDO.getCreatedType())
                .gid(requestParam.getGid())
                .originUrl(requestParam.getOriginUrl())
                .describe(requestParam.getDescribe())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .build();
//        比较是否更改了gid，如果更改了gid，则需要先删除在重新写入
        if (Objects.equals(hasShortLinkDO.getGid(),requestParam.getGid())){
//            相同，则说明没有改gid，直接修改需要更改的数据就行
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, requestParam.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .set(Objects.equals(requestParam.getValidDateType(), VailDateTypeEnum.PERMANENT.getType()), ShortLinkDO::getValidDate, null);
            baseMapper.update(shortLinkDO,updateWrapper);
        }else {
//            不同，说明改了gid，得先删除
//            先查出来
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, requestParam.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0);
            baseMapper.delete(updateWrapper);
//            在重新插入
            baseMapper.insert(shortLinkDO);
        }
    }

   @SneakyThrows
    @Override
    public void restoreUrl(String shortUri, ServletRequest request, ServletResponse response){
        String serverName = request.getServerName();
//        String serverPort = Optional.of(request.getServerPort())
//                .filter(each -> !Objects.equals(each,80))
//                .map(String::valueOf)
//                .map(each -> ":" + each)
//                .orElse("");
        String fullShortUrl = serverName + "/" + shortUri;
        String originalLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_SHORT_LINK_KEY,fullShortUrl));
//        先查缓存，如果缓存有则直接跳转
        if (StrUtil.isNotBlank(originalLink)){
            ((HttpServletResponse) response).sendRedirect(originalLink);
            return;
        }
        boolean isContains = shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl);
        if (!isContains){
            ((HttpServletResponse) response).sendRedirect("/page/notfound");
            return;
        }
        String gotoIsNull = stringRedisTemplate.opsForValue().get(String.format(GOTO_IS_NULL_SHORT_LINK_KEY,fullShortUrl));
        if (StrUtil.isNotBlank(gotoIsNull)){
            ((HttpServletResponse) response).sendRedirect("/page/notfound");
            return;
        }

//            解决缓存击穿，加分布式锁
        RLock lock = redissonClient.getLock(String.format(LOCK_GOTO_SHORT_LINK_KEY,fullShortUrl));
        lock.lock();
        try {
            originalLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_SHORT_LINK_KEY,fullShortUrl));
            if (StrUtil.isNotBlank(originalLink)){
                ((HttpServletResponse) response).sendRedirect(originalLink);
                return;
            }
            LambdaQueryWrapper<ShortLinkGotoDO> linkGotoDOWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class)
                    .eq(ShortLinkGotoDO::getFullShortUrl,fullShortUrl);
            ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(linkGotoDOWrapper);
            if (shortLinkGotoDO != null){
                stringRedisTemplate.opsForValue().set(String.format(GOTO_IS_NULL_SHORT_LINK_KEY,fullShortUrl),"-", 30, TimeUnit.MINUTES);
                ((HttpServletResponse) response).sendRedirect("/page/notfound");
                return;
            }
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getGid,shortLinkGotoDO.getGid())
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
                    .eq(ShortLinkDO::getEnableStatus,0)
                    .eq(ShortLinkDO::getDelFlag,0);
            ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
//        短链接表内有数据，则进行跳转
            if (shortLinkDO != null){
                if (shortLinkDO.getValidDate() != null && shortLinkDO.getValidDate().before(new Date())){
                    stringRedisTemplate.opsForValue().set(String.format(GOTO_IS_NULL_SHORT_LINK_KEY,fullShortUrl),"-", 30, TimeUnit.MINUTES);
                    return;
                }
                stringRedisTemplate.opsForValue().set(
                        String.format(GOTO_SHORT_LINK_KEY,fullShortUrl),
                        shortLinkDO.getOriginUrl(),
                        LinkUtil.getLinkCacheValidDate(shortLinkDO.getValidDate()), TimeUnit.MILLISECONDS
                );
                ((HttpServletResponse) response).sendRedirect(shortLinkDO.getOriginUrl());
            }
        }finally {
            lock.unlock();
        }
    }

    public String generateSuffix(ShortLinkCreateReqDTO requestParam){
        int customGenerateCount = 0;
        String shortUri;
        while (true){
            if (customGenerateCount > 10){
                throw new ServiceException("短链接创建频繁，请稍候再试");
            }
            String originUrl = requestParam.getOriginUrl();
            originUrl += System.currentTimeMillis();
            shortUri = HashUtil.hashToBase62(originUrl);

            /*
             * 如果在布龙过滤器中找到对应短链接，则再次创建，否，则返回创建好的短链接
             */
            if (!shortUriCreateCachePenetrationBloomFilter.contains(requestParam.getDomain() + "/" + shortUri)){
                break;
            }
            customGenerateCount++;
        }
        return shortUri;

    }
}
