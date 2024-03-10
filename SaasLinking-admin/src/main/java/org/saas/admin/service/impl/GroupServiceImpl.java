package org.saas.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.saas.admin.dao.entity.GroupDO;
import org.saas.admin.dao.mapper.GroupMapper;
import org.saas.admin.service.GroupService;
import org.saas.admin.tookit.RadomGenerator;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

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
                .name(groupName)
                .sortOrder(0)
                .build();
        baseMapper.insert(group);
    }
    public boolean hasGid(String gid){
        LambdaQueryWrapper<GroupDO> query =  Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid,gid)
                .eq(GroupDO::getUsername,null);

        GroupDO hasGroupGid = baseMapper.selectOne(query);
        return hasGroupGid == null;
    }
}
