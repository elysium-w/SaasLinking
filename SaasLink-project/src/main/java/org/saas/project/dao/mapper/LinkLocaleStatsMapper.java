package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.saas.project.dao.entity.LinkLocaleStatsDO;

/**
 * 短链接访问地区监控记录
 */
public interface LinkLocaleStatsMapper extends BaseMapper<LinkLocaleStatsDO> {
    /**
     * 新增访问位置监控记录
     */
    void shortLinkLocaleState(@Param("linkLocaleStats") LinkLocaleStatsDO linkLocaleStatsDO);
}
