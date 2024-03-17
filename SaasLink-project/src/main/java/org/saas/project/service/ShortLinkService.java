package org.saas.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.saas.project.dao.entity.ShortLinkDO;
import org.saas.project.dto.req.ShortLinkCreateReqDTO;
import org.saas.project.dto.req.ShortLinkPageReqDTO;
import org.saas.project.dto.req.ShortLinkUpdateReqDTO;
import org.saas.project.dto.resp.ShortLinkCreateRespDTO;
import org.saas.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.saas.project.dto.resp.ShortLinkPageRespDTO;

import java.util.List;

public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 创建短链接
     * @param requestParam 短链接创建参数
     * @return 短链接创建成功参数
     */
    ShortLinkCreateRespDTO createLink(ShortLinkCreateReqDTO requestParam);

    /**
     * 分页查询短链接
     * @param requestParam 请求参数
     * @return 分页返回结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam);

    /**
     * 查看短链接分组数量
     * @param requestParam 查询短链接分组数量请求参数
     * @return 分组数量
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> requestParam);

    /**
     * 修改短链接
     * @param requestParam 短链接修改参数
     */
    void updateShortLink(ShortLinkUpdateReqDTO requestParam);

    /**
     * 短链接跳转原长链接
     * @param shortUri 短链接后缀
     * @param request  http请求报文
     * @param response http回复报文
     */
    void restoreUrl(String shortUri, ServletRequest request, ServletResponse response);
}
