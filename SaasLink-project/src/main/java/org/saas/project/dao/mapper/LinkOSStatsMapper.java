package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.saas.project.dao.entity.LinkOSStatsDO;

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
}
