package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.saas.project.dao.entity.LinkAccessStatsDO;

/**
 * 短链接基础访问监控持久层
 */
public interface LinkAccessStatsMapper extends BaseMapper<LinkAccessStatsMapper> {
    /**
     * 基础访问监控数据
     */
    @Insert("INSERT INTO t_link_access_stats (full_short_url, gid, date, pv, uv, uip, hour, weekday, create_time, update_time, del_flag)" +
            "VALUES (#{linkAccessStats.fullshortUrl},#{linkAccessStats.gid},#{linkAccessStats.date}, #{linkAccessStats.pv}, #{linkAccessStats.uv}, #{linkAccessStats.uip}, #{linkAccessStats.hour}, #{linkAccessStats.weekday}, NOW(), NOW(), 0) ON DUPLICATE KEY UPDATE pv = pv +  #{linkAccessStats.pv}, \" +\n" +
            "            \"uv = uv + #{linkAccessStats.uv}, \" +\n" +
            "            \" uip = uip + #{linkAccessStats.uip};)")
    void shortLinkStats(@Param("linkAccessStats") LinkAccessStatsDO linkAccessStatsDO);
}
