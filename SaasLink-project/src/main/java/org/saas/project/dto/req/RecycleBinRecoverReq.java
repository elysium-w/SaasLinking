package org.saas.project.dto.req;

import lombok.Data;

/**
 * 恢复短链接参数
 */
@Data
public class RecycleBinRecoverReq {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}
