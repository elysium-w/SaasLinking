package org.saas.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.project.dao.entity.ShortLinkDO;
import org.saas.project.dto.req.ShortLinkCreateReqDTO;
import org.saas.project.dto.resp.ShortLinkCreateRespDTO;

public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 创建短链接
     * @param requestParam 短链接创建参数
     * @return 短链接创建成功参数
     */
    ShortLinkCreateRespDTO createLink(ShortLinkCreateReqDTO requestParam);
}