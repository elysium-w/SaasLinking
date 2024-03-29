package org.saas.project.dto.req;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 修改短链接参数
 */
@Data
@RequiredArgsConstructor
public class RecycleBinReqDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

}
