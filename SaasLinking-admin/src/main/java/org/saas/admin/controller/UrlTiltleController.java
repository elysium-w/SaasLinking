package org.saas.admin.controller;

import lombok.RequiredArgsConstructor;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.remote.ShortLinkRemoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 根据url获取网站名称控制器
 */
@Controller
@RequiredArgsConstructor
public class UrlTiltleController {
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };
    @GetMapping("/api/link/admin/v1/title")
    public Result<String> getTitleFromUrl(@RequestParam("url") String url){
        return shortLinkRemoteService.getTitleFromUrl(url);
    }
}
