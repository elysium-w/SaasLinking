package org.saas.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import java.util.List;
import org.saas.project.dao.entity.ShortLinkDO;

/**
 * 回收站短链接分页查询
 */
@Data
public class ShortLinkRecycleBinPageReqDTO extends Page<ShortLinkDO> {

    /**
     * 分组标识
     */
    private List<String> gidList;
}
