package org.saas.project.dto.resp;

import lombok.Data;

/**
 * 短链接分组查询
 */
@Data
public class ShortLinkGroupCountQueryRespDTO {
    /**
     * 短链接分组id
     */
    private String gid;
    /**
     * 短链接数量
     */
    private Integer shortLinkCount;
}
