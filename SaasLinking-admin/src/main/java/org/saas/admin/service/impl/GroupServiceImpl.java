package org.saas.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.saas.admin.dao.entity.GroupDO;
import org.saas.admin.dao.mapper.GroupMapper;
import org.saas.admin.service.GroupService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper,GroupDO> implements GroupService {
    @Override
    public void saveGroup(String groupName) {

    }
}
