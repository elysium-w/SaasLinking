package org.saas.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.saas.project.common.conversion.result.Result;
import org.saas.project.common.conversion.result.Results;
import org.saas.project.dto.req.ShortLinkCreateReqDTO;
import org.saas.project.dto.req.ShortLinkPageReqDTO;
import org.saas.project.dto.resp.ShortLinkCreateRespDTO;
import org.saas.project.dto.resp.ShortLinkPageRespDTO;
import org.saas.project.service.ShortLinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService linkService;

    /**
     * 创建短链接
     */
    @PostMapping("/api/link/project/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam){
        return Results.success(linkService.createLink(requestParam));
    }
    @GetMapping("/api/link/project/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        return Results.success(linkService.pageShortLink(requestParam));
    }

}
