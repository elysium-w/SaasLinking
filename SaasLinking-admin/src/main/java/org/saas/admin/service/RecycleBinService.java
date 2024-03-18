package org.saas.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import org.saas.admin.remote.dto.resp.ShortLinkPageRespDTO;

public interface RecycleBinService {
    /**
     * 分页查询短链接
     * @param requestParam 请求参数
     * @return 分页返回结果
     */

    Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam);
}
