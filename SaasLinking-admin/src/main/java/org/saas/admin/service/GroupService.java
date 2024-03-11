package org.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.admin.dao.entity.GroupDO;
import org.saas.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import org.saas.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

public interface GroupService extends IService<GroupDO> {
    /**
     * 新增用户短链接分组
     * @param groupName
     */
    void saveGroup(String groupName);

    /**
     * 查询用户端连接分组集合
     * @return list
     */
    List<ShortLinkGroupRespDTO> listGroup();

    /**
     * 修改短链接分组
     * @param requestParam
     */
    void updateGroup(ShortLinkGroupUpdateReqDTO requestParam);
}
