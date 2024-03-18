package org.saas.admin.dto.req;

import lombok.Data;

/**
 * 回收站删除短链接参数
 */
@Data
public class RecycleBinRemoveReq {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 全部短链接
     */
    private String fullShortUrl;
}
