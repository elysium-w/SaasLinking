package org.saas.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 回收站短链接分页查询
 */
@Data
public class ShortLinkRecycleBinPageReqDTO extends Page {

    /**
     * 分组标识
     */
    private List<String> gidList;
}
