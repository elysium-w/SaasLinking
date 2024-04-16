package org.saas.project.service;

import org.saas.project.dto.req.ShortLinkStatsReqDTO;
import org.saas.project.dto.resp.ShortLinkStatsRespDTO;
import org.springframework.stereotype.Service;

/**
 * 短链接监控接口层
 */
public interface ShortLinkStatsService {
    /**
     * 获取单个短链接访问监控
     * @param requestParam 短链接访问请求参数
     * @return 短链接访问监控返回参数
     */
    ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam);
}
