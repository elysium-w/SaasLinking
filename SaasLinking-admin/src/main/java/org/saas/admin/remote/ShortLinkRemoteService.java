package org.saas.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.dto.req.RecycleBinReqDTO;
import org.saas.admin.remote.dto.req.ShortLinkCreateReqDTO;
import org.saas.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.saas.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import org.saas.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import org.saas.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import org.saas.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短链接中台远程调用服务
 */
public interface ShortLinkRemoteService {
    /**
     * 新增短链接
     * @param requestParam 新建短链接参数
     * @return 短链接创建响应
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO requestParam){
        String resultBody = HttpUtil.post("http://127.0.0.1:8001/api/link/project/v1/create", JSON.toJSONString(requestParam));
        return JSON.parseObject(resultBody, new TypeReference<>() {
        });
    }

    /**
     * 短链接分页查询
     * @param requestParam 分页查询参数
     * @return 分页查询结果
     */

    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid",requestParam.getGid());
        requestMap.put("current", requestParam.getCurrent());
        requestMap.put("size", requestParam.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/link/project/v1/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     *
     * @param requestParam 查询分组内数量参数
     * @return 查询分组内数量响应结果
     */
    default Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(List<String> requestParam){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("requestParam",requestParam);
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/link/project/v1/count", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }
    /**
     * 修改短链接
     * @param requestParam 修改短链接参数
     */
    default void updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam){
        HttpUtil.post("http://127.0.0.1:8001/api/link/project/v1/update", JSON.toJSONString(requestParam));
    }

    /**
     * 根据url获取标题
     * @param url url
     * @return 网站标题
     */
    default Result<String> getTitleFromUrl(@RequestParam("url") String url){
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/link/project/v1/title?url=" + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }


    default void saveRecycleBin(RecycleBinReqDTO requestParam){
        HttpUtil.post("http://127.0.0.1:8001/api/link/v1/recycle-bin/save", JSON.toJSONString(requestParam));
    }
}
