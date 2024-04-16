package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.saas.project.dao.entity.LinkNetworkStatsDO;

/**
 *短链接访问网络监控记录
 */
public interface LinkNetworkStatsMapper extends BaseMapper<LinkNetworkStatsDO> {
    /**
     * 新增短链接访问网络监控记录
     */
    @Insert("INSERT INTO " +
            "t_link_network_stats (full_short_url, date, cnt, network, create_time, update_time, del_flag) " +
            "VALUES( #{linkNetworkStats.fullShortUrl}, #{linkNetworkStats.date}, #{linkNetworkStats.cnt}, #{linkNetworkStats.network}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt +  #{linkNetworkStats.cnt};")
    void shortLinkNetworkState(@Param("linkNetworkStats")LinkNetworkStatsDO linkNetworkStatsDO);

}
