package org.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.admin.dao.entity.GroupDO;

public interface GroupService extends IService<GroupDO> {
    void saveGroup(String groupName);
}
