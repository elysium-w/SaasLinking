package org.saas.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.remote.dto.ShortLinkRemoteService;
import org.saas.admin.remote.dto.req.ShortLinkCreateReqDTO;
import org.saas.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.saas.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import org.saas.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortLinkController {
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };
    /**
     * 创建短链接
     */
    @PostMapping("/api/link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam){
        return shortLinkRemoteService.createShortLink(requestParam);
    }
    @GetMapping("/api/link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        return shortLinkRemoteService.pageShortLink(requestParam);
    }
}