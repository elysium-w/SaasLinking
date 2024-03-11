package org.saas.admin.controller;

import lombok.RequiredArgsConstructor;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.common.conversion.result.Results;
import org.saas.admin.dto.req.ShortLinkGroupSaveReqDTO;
import org.saas.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import org.saas.admin.dto.resp.ShortLinkGroupRespDTO;
import org.saas.admin.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/link/v1/group")
    public Result<Void> saveGroup(@RequestBody ShortLinkGroupSaveReqDTO requestParam){
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }

    /**
     * 查询短链接分组集合
     * @return 短链接集合
     */
    @GetMapping("/api/link/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup(){
        return Results.success(groupService.listGroup());
    }

    /**
     * 修改短链接分组名
     * @param requestParam 修改短链接分组名请求体
     * @return 修改结果
     */
    @PutMapping("/api/link/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam){
        groupService.updateGroup(requestParam);
        return Results.success();
    }

    /**
     * 删除短链接分组
     * @param gid 短链接分组标识
     * @return 删除结果
     */
    @DeleteMapping("/api/link/v1/group")
    public Result<Void> deleteGroup(@RequestParam String gid){
        groupService.deleteGroup(gid);
        return Results.success();
    }

//    @PostMapping("/api/link/v1/group")
//    public Result<Void> sortGroup(){
//        return Results.success();
//    }
}
