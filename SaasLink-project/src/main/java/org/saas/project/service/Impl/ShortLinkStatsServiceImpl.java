package org.saas.project.service.Impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.saas.project.dao.entity.LinkAccessStatsDO;
import org.saas.project.dao.mapper.LinkAccessStatsMapper;
import org.saas.project.dto.req.ShortLinkStatsReqDTO;
import org.saas.project.dto.resp.ShortLinkStatsRespDTO;
import org.saas.project.service.ShortLinkStatsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接监控接口实现层
 */
@Service
@RequiredArgsConstructor
public class ShortLinkStatsServiceImpl implements ShortLinkStatsService {
    private final LinkAccessStatsMapper linkAccessStatsMapper;
    @Override
    public ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam) {
//        List<LinkAccessStatsDO> listShorLinkByDO = linkAccessStatsMapper
        return null;
    }
}
