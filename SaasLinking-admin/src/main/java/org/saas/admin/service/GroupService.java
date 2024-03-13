package org.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.admin.dao.entity.GroupDO;
import org.saas.admin.dto.req.ShortLinkGroupSortReqDTO;
import org.saas.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import org.saas.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

public interface GroupService extends IService<GroupDO> {
    /**
     * 新增用户短链接分组
     * @param groupName 分组命名
     */
    void saveGroup(String groupName);
    /**
     * 新增短链接分组
     *
     * @param username  用户名
     * @param groupName 短链接分组名
     */
    void saveGroup(String username, String groupName);
    /**
     * 查询用户端连接分组集合
     * @return list
     */
    List<ShortLinkGroupRespDTO> listGroup();

    /**
     * 修改短链接分组
     * @param requestParam 修改信息
     */
    void updateGroup(ShortLinkGroupUpdateReqDTO requestParam);

    /**
     * 删除短链接分组
     * @param gid 短链接分组标识
     */
    void deleteGroup(String gid);

    /**
     * 短链接分组排序
     * @param requestParam 短链接分组排序参数
     */
    void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam);
}
