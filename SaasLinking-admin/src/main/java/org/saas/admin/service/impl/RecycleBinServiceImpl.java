package org.saas.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.saas.admin.common.biz.user.UserContext;
import org.saas.admin.common.conversion.exception.ServiceException;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.dao.entity.GroupDO;
import org.saas.admin.dao.mapper.GroupMapper;
import org.saas.admin.remote.ShortLinkRemoteService;
import org.saas.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import org.saas.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.saas.admin.service.RecycleBinService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 回收站接口实现层
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {
    private final GroupMapper groupMapper;
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };


    @Override
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag,0);
        List<GroupDO> groupDOList = groupMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupDOList)){
            throw new ServiceException("用户无分组");
        }
        requestParam.setGidList(groupDOList.stream().map(GroupDO::getGid).toList());
        return shortLinkRemoteService.pageRecycleBinShortLink(requestParam);
    }
}
