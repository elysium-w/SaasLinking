package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.saas.project.dao.entity.LinkDeviceStatsDO;
import org.saas.project.dto.req.ShortLinkStatsReqDTO;

import java.util.List;

/**
 * 短链接用户访问设备监控记录
 */
public interface LinkDeviceStatsMapper extends BaseMapper<LinkDeviceStatsDO> {
    /**
     * 访问设备监控记录
     * @param linkDeviceStatsDO
     */

    @Insert("INSERT INTO " +
            "t_link_device_stats (full_short_url, date, cnt, device, create_time, update_time, del_flag) " +
            "VALUES( #{linkDeviceStats.fullShortUrl}, #{linkDeviceStats.date}, #{linkDeviceStats.cnt}, #{linkDeviceStats.device}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt +  #{linkDeviceStats.cnt};")
    void shortLinkDeviceState(@Param("linkDeviceStats") LinkDeviceStatsDO linkDeviceStatsDO);

    /**
     * 根据短链接获取指定日期内访问设备监控数据
     */
    @Select("SELECT " +
            "    tlds.device, " +
            "    SUM(tlds.cnt) AS cnt " +
            "FROM " +
            "    t_link tl INNER JOIN " +
            "    t_link_device_stats tlds ON tl.full_short_url = tlds.full_short_url " +
            "WHERE " +
            "    tlds.full_short_url = #{param.fullShortUrl} " +
            "    AND tl.gid = #{param.gid} " +
            "    AND tl.del_flag = '0' " +
            "    AND tl.enable_status = #{param.enableStatus} " +
            "    AND tlds.date BETWEEN #{param.startDate} and #{param.endDate} " +
            "GROUP BY " +
            "    tlds.full_short_url, tl.gid, tlds.device;")
    List<LinkDeviceStatsDO> listDeviceStatsByShortLink(@Param("param")ShortLinkStatsReqDTO requestParam);

}
