package org.saas.project.controller;

import lombok.RequiredArgsConstructor;
import org.saas.project.common.conversion.result.Result;
import org.saas.project.common.conversion.result.Results;
import org.saas.project.dto.req.ShortLinkStatsReqDTO;
import org.saas.project.dto.resp.ShortLinkStatsRespDTO;
import org.saas.project.service.ShortLinkStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkStatsController {
    private final ShortLinkStatsService shortLinkStatsService;
    /**
     * 访问单个短链接指定时间内监控数据
     */
    @GetMapping("/api/link/v1/stats")
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO requestParam){
        return Results.success(shortLinkStatsService.oneShortLinkStats(requestParam));
    }

}
