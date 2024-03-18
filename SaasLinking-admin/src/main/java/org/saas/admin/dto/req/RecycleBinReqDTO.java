package org.saas.admin.dto.req;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 修改短链接参数
 */
@Data
@RequiredArgsConstructor
public class RecycleBinReqDTO {

//    TODO ：可以优化一下使用UserContext校验用户名防止修改出错
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

}
