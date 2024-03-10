package org.saas.admin.controller;

import lombok.RequiredArgsConstructor;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.common.conversion.result.Results;
import org.saas.admin.dto.req.ShortLinkGroupSaveReqDTO;
import org.saas.admin.dto.resp.ShortLinkGroupRespDTO;
import org.saas.admin.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @PostMapping("/api/link/v1/group")
    public Result<Void> saveGroup(@RequestBody ShortLinkGroupSaveReqDTO requestParam){
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }
    @GetMapping("/api/link/v1/group/{name}")
    public Result<ShortLinkGroupRespDTO> getGroupByGroupName(){
        g
    }
}
