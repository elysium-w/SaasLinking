package org.saas.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.saas.admin.common.biz.user.UserContext;
import org.saas.admin.dao.entity.GroupDO;
import org.saas.admin.dao.mapper.GroupMapper;
import org.saas.admin.dto.resp.ShortLinkGroupRespDTO;
import org.saas.admin.service.GroupService;
import org.saas.admin.tookit.RadomGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper,GroupDO> implements GroupService {
    @Override
    public void saveGroup(String groupName) {
        String gid;
        do {
            gid = RadomGenerator.generateRandomDigits();
        }while (!hasGid(gid));
        GroupDO group = GroupDO.builder()
                .gid(gid)
                .username(UserContext.getUsername())
                .name(groupName)
                .sortOrder(0)
                .build();
        baseMapper.insert(group);
    }

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag,0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder,GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
    }

    public boolean hasGid(String gid){
        LambdaQueryWrapper<GroupDO> query =  Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid,gid)
                .eq(GroupDO::getUsername,null);

        GroupDO hasGroupGid = baseMapper.selectOne(query);
        return hasGroupGid == null;
    }
}
