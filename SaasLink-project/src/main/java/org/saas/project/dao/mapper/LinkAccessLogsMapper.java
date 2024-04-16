package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.saas.project.dao.entity.LinkAccessLogsDO;
import org.saas.project.dao.entity.LinkAccessStatsDO;
import org.saas.project.dto.req.ShortLinkStatsReqDTO;

import java.util.HashMap;
import java.util.List;

/**
 *访问日志持久层
 */
public interface LinkAccessLogsMapper extends BaseMapper<LinkAccessLogsDO> {
    /**
     *根据短链接获取指定日期内高频访问ip
     */
    @Select("SELECT " +
            "    tlal.ip, " +
            "    COUNT(tlal.ip) AS count " +
            "FROM " +
            "    t_link tl INNER JOIN " +
            "    t_link_access_logs tlal ON tl.full_short_url = tlal.full_short_url " +
            "WHERE " +
            "    tlal.full_short_url = #{param.fullShortUrl} " +
            "    AND tl.gid = #{param.gid} " +
            "    AND tl.del_flag = '0' " +
            "    AND tl.enable_status = #{param.enableStatus} " +
            "    AND tlal.create_time BETWEEN #{param.startDate} and #{param.endDate} " +
            "GROUP BY " +
            "    tlal.full_short_url, tl.gid, tlal.ip " +
            "ORDER BY " +
            "    count DESC " +
            "LIMIT 5;")
    List<HashMap<String, Object>> listTopIPByShortLink(@Param("param")ShortLinkStatsReqDTO requestParam);

    /**
     * 根据短链接获取基础访问监控记录PV,UV,UIP
     */
    @Select("SELECT "+
            "    COUNT(tlal.user) AS pv, " +
            "    COUNT(DISTINCT tlal.user) AS uv, " +
            "    COUNT(DISTINCT tlal.ip) AS uip " +
            "FROM " +
            "    t_link tl INNER JOIN " +
            "    t_link_access_logs tlal ON tl.full_short_url = tlal.full_short_url " +
            "WHERE " +
            "    tlal.full_short_url = #{param.fullShortUrl} " +
            "    AND tl.gid = #{param.gid} " +
            "    AND tl.del_flag = '0' " +
            "    AND tl.enable_status = #{param.enableStatus} " +
            "    AND tlal.create_time BETWEEN #{param.startDate} and #{param.endDate} " +
            "GROUP BY " +
            "    tlal.full_short_url, tl.gid;")
    LinkAccessStatsDO findPvUvUidStatsByShortLink(@Param("param")ShortLinkStatsReqDTO requestParam);
}
