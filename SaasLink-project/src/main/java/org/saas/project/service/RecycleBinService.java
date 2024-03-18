package org.saas.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.project.dao.entity.ShortLinkDO;
import org.saas.project.dto.req.RecycleBinReqDTO;

public interface RecycleBinService extends IService<ShortLinkDO> {
    void saveRecycleBin(RecycleBinReqDTO requestParam);
}
