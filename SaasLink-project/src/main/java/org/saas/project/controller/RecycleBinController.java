package org.saas.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.saas.project.common.conversion.result.Result;
import org.saas.project.common.conversion.result.Results;
import org.saas.project.dto.req.RecycleBinReqDTO;
import org.saas.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import org.saas.project.dto.resp.ShortLinkPageRespDTO;
import org.saas.project.service.RecycleBinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    /**
     * 保存短链接到回收站
     */
    @PostMapping("/api/link/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinReqDTO requestParam){
        recycleBinService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 回收站短链接分页查询
     */
    @GetMapping("/api/link/v1/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBin(ShortLinkRecycleBinPageReqDTO requestParam){
        return Results.success(recycleBinService.pageRecycleBin(requestParam));
    }


    /**
     * 恢复短链接
     */
    public Result<Void> recoverRecycleBin(){
        return Results.success();
    }
}
