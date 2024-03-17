package org.saas.project.controller;

import lombok.RequiredArgsConstructor;
import org.saas.project.common.conversion.result.Result;
import org.saas.project.common.conversion.result.Results;
import org.saas.project.service.UrlTitleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 根据url获取网站名称控制器
 */
@Controller
@RequiredArgsConstructor
public class UrlTiltleController {
    private final UrlTitleService urlTitleService;
    @GetMapping("/api/link/project/v1/title")
    public Result<String> getTitleFromUrl(@RequestParam("url") String url){
        return Results.success(urlTitleService.getTitleFromUrl(url));
    }
}
