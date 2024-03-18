package org.saas.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.saas.project.dao.entity.ShortLinkDO;
import org.saas.project.dto.req.RecycleBinRecoverReq;
import org.saas.project.dto.req.RecycleBinRemoveReq;
import org.saas.project.dto.req.RecycleBinReqDTO;
import org.saas.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import org.saas.project.dto.resp.ShortLinkPageRespDTO;

public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 短链接移至回收站
     * @param requestParam 短链接参数
     */
    void saveRecycleBin(RecycleBinReqDTO requestParam);

    /**
     * 分页查询短链接
     * @param requestParam 请求参数
     * @return 分页返回结果
     */
    IPage<ShortLinkPageRespDTO> pageRecycleBin(ShortLinkRecycleBinPageReqDTO requestParam);

    /**
     * 回收站恢复短链接
     * @param requestParam 短链接参数
     */
    void recoverRecycleBin(RecycleBinRecoverReq requestParam);

    /**
     * 删除短链接
     * @param requestParam 短链接参数
     */
    void removeRecycleBin(RecycleBinRemoveReq requestParam);
}
