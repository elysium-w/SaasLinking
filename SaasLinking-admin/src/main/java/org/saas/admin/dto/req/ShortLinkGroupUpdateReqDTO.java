package org.saas.admin.dto.req;

import lombok.Data;

/**
 * 修改参数
 */
@Data
public class ShortLinkGroupUpdateReqDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;
}
