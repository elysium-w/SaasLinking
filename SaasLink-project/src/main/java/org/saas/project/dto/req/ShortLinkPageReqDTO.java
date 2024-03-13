package org.saas.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.saas.project.dao.entity.ShortLinkDO;

@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {
    /**
     * 分组标识
     */
    private String gid;

}
