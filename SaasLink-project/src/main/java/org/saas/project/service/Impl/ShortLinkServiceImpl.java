package org.saas.project.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.saas.project.dao.entity.ShortLinkDO;
import org.saas.project.dao.mapper.LinkMapper;
import org.saas.project.dto.req.ShortLinkCreateReqDTO;
import org.saas.project.dto.resp.ShortLinkCreateRespDTO;
import org.saas.project.service.ShortLinkService;
import org.saas.project.tookit.HashUtil;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShortLinkServiceImpl extends ServiceImpl<LinkMapper, ShortLinkDO> implements ShortLinkService {
    @Override
    public ShortLinkCreateRespDTO createLink(ShortLinkCreateReqDTO requestParam) {
        String shortLinkSuffix = generateSuffix(requestParam);
        ShortLinkDO linkDO = BeanUtil.toBean(requestParam, ShortLinkDO.class);
        linkDO.setShortUri(shortLinkSuffix);
        linkDO.setFullShortUrl(requestParam.getDomain() + "/" + shortLinkSuffix);
        baseMapper.insert(linkDO);
        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(linkDO.getFullShortUrl())
                .originUrl(requestParam.getOriginUrl())
                .build();
    }
    public String generateSuffix(ShortLinkCreateReqDTO requestParam){
        String originUrl = requestParam.getOriginUrl();
        return HashUtil.hashToBase62(originUrl);
    }
}
