package org.saas.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.saas.project.common.conversion.result.Result;
import org.saas.project.common.conversion.result.Results;
import org.saas.project.dto.req.ShortLinkCreateReqDTO;
import org.saas.project.dto.req.ShortLinkPageReqDTO;
import org.saas.project.dto.req.ShortLinkUpdateReqDTO;
import org.saas.project.dto.resp.ShortLinkCreateRespDTO;
import org.saas.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.saas.project.dto.resp.ShortLinkPageRespDTO;
import org.saas.project.service.ShortLinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接控制层
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService linkService;
    /**
     * 短链接跳转原始链接
     */
    @GetMapping("/{short-uri}")
    public void restoreUrl(@PathVariable("short-uri") String shortUri, ServletRequest request, ServletResponse response){
        linkService.restoreUrl(shortUri,request,response);
    }

    /**
     * 创建短链接
     */
    @PostMapping("/api/link/project/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam){
        return Results.success(linkService.createLink(requestParam));
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/link/project/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        return Results.success(linkService.pageShortLink(requestParam));
    }
    /**
     * 查询短链接分组内数量
     */
    @GetMapping("/api/link/project/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam){
        return Results.success(linkService.listGroupShortLinkCount(requestParam));
    }
    /**
     * 修改短链接
     */
    @PostMapping("/api/link/project/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam){
        linkService.updateShortLink(requestParam);
        return Results.success();
    }
}
