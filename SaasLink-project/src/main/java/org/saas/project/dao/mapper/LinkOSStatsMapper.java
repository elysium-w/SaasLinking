package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.saas.project.dao.entity.LinkOSStatsDO;
import org.saas.project.dto.req.ShortLinkStatsReqDTO;

import java.util.HashMap;
import java.util.List;

/**
 * 短链接访问操作系统监控记录
 */
public interface LinkOSStatsMapper extends BaseMapper<LinkOSStatsDO> {
    /**
     * 新增短链接访问操作系统监控
     */

    @Insert("INSERT INTO " +
            "t_link_os_stats (full_short_url, date, cnt, os, create_time, update_time, del_flag) " +
            "VALUES( #{linkOsStats.fullShortUrl}, #{linkOsStats.date}, #{linkOsStats.cnt}, #{linkOsStats.os}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt +  #{linkOsStats.cnt};")
    void shortLinkOSState(@Param("linkOsStats")LinkOSStatsDO linkOSStatsDO);

    /**
     * 根据短链接查询指定日期内操作系统监控数据
     */
    @Select("SELECT " +
            "    tlos.os, " +
            "    SUM(tlos.cnt) AS count " +
            "FROM " +
            "    t_link tl INNER JOIN " +
            "    t_link_os_stats tlos ON tl.full_short_url = tlos.full_short_url " +
            "WHERE " +
            "    tlos.full_short_url = #{param.fullShortUrl} " +
            "    AND tl.gid = #{param.gid} " +
            "    AND tl.del_flag = '0' " +
            "    AND tl.enable_status = #{param.enableStatus} " +
            "    AND tlos.date BETWEEN #{param.startDate} and #{param.endDate} " +
            "GROUP BY " +
            "    tlos.full_short_url, tl.gid, tlos.os;")
    List<HashMap<String,Object>> listOSStatsByShortLink(@Param("param")ShortLinkStatsReqDTO requestParam);
}
