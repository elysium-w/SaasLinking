package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.saas.project.dao.entity.LinkAccessStatsDO;
import org.saas.project.dto.req.ShortLinkGroupStatsReqDTO;
import org.saas.project.dto.req.ShortLinkStatsReqDTO;

import java.util.HashMap;
import java.util.List;

/**
 * 短链接基础访问监控持久层
 */
public interface LinkAccessStatsMapper extends BaseMapper<LinkAccessStatsDO> {
    /**
     * 基础访问监控数据
     */
    @Insert("INSERT INTO t_link_access_stats (full_short_url, gid, date, pv, uv, uip, hour, weekday, create_time, update_time, del_flag)" +
            "VALUES (#{linkAccessStats.fullshortUrl},#{linkAccessStats.gid},#{linkAccessStats.date}, #{linkAccessStats.pv}, #{linkAccessStats.uv}, #{linkAccessStats.uip}, #{linkAccessStats.hour}, #{linkAccessStats.weekday}, NOW(), NOW(), 0) ON DUPLICATE KEY UPDATE pv = pv +  #{linkAccessStats.pv}, \" +\n" +
            "            \"uv = uv + #{linkAccessStats.uv}, \" +\n" +
            "            \" uip = uip + #{linkAccessStats.uip};)")
    void shortLinkStats(@Param("linkAccessStats") LinkAccessStatsDO linkAccessStatsDO);

    /**
     * 根据短链接获取指定日期内基础监控数据
     */
    @Select("SELECT " +
            "    tlas.date, " +
            "    SUM(tlas.pv) AS pv, " +
            "    SUM(tlas.uv) AS uv, " +
            "    SUM(tlas.uip) AS uip " +
            "FROM " +
            "    t_link tl INNER JOIN " +
            "    t_link_access_stats tlas ON tl.full_short_url = tlas.full_short_url " +
            "WHERE " +
            "    tlas.full_short_url = #{param.fullShortUrl} " +
            "    AND tl.gid = #{param.gid} " +
            "    AND tl.del_flag = '0' " +
            "    AND tl.enable_status = #{param.enableStatus} " +
            "    AND tlas.date BETWEEN #{param.startDate} and #{param.endDate} " +
            "GROUP BY " +
            "    tlas.full_short_url, tl.gid, tlas.date;")
    List<LinkAccessStatsDO> listStatsByShortLink(@Param("param")ShortLinkStatsReqDTO requestParam);

//    /**
//     * 根据分组获得指定日期内监控数据
//     */
//    @Select("SELECT " +
//            "    tlas.date, " +
//            "    SUM(tlas.pv) AS pv, " +
//            "    SUM(tlas.uv) AS uv, " +
//            "    SUM(tlas.uip) AS uip " +
//            "FROM " +
//            "    t_link tl INNER JOIN " +
//            "    t_link_access_stats tlas ON tl.full_short_url = tlas.full_short_url " +
//            "WHERE " +
//            "    tl.gid = #{param.gid} " +
//            "    AND tl.del_flag = '0' " +
//            "    AND tl.enable_status = '0' " +
//            "    AND tlas.date BETWEEN #{param.startDate} and #{param.endDate} " +
//            "GROUP BY " +
//            "    tl.gid, tlas.date;")
//    List<LinkAccessStatsDO> listStatsByGroup(@Param("param")ShortLinkGroupStatsReqDTO requestParam);

    @Select("SELECT " +
            "    tlas.weekday, " +
            "    SUM(tlas.pv) AS pv " +
            "FROM " +
            "    t_link tl INNER JOIN " +
            "    t_link_access_stats tlas ON tl.full_short_url = tlas.full_short_url " +
            "WHERE " +
            "    tlas.full_short_url = #{param.fullShortUrl} " +
            "    AND tl.gid = #{param.gid} " +
            "    AND tl.del_flag = '0' " +
            "    AND tl.enable_status = #{param.enableStatus} " +
            "    AND tlas.date BETWEEN #{param.startDate} and #{param.endDate} " +
            "GROUP BY " +
            "    tlas.full_short_url, tl.gid, tlas.weekday;")
    List<LinkAccessStatsDO> listWeekdayStatsBySHortLink(@Param("param")ShortLinkStatsReqDTO requestParam);

    /**
     * 根据短链接获取指定日期内新旧访客数据
     */

    @Select("SELECT " +
            "    SUM(old_user) AS oldUserCnt, " +
            "    SUM(new_user) AS newUserCnt " +
            "FROM ( " +
            "    SELECT " +
            "        CASE WHEN COUNT(DISTINCT DATE(tlal.create_time)) > 1 THEN 1 ELSE 0 END AS old_user, " +
            "        CASE WHEN COUNT(DISTINCT DATE(tlal.create_time)) = 1 AND MAX(tlal.create_time) >= #{param.startDate} AND MAX(tlal.create_time) <= #{param.endDate} THEN 1 ELSE 0 END AS new_user " +
            "    FROM " +
            "        t_link tl INNER JOIN " +
            "        t_link_access_logs tlal ON tl.full_short_url = tlal.full_short_url " +
            "    WHERE " +
            "        tlal.full_short_url = #{param.fullShortUrl} " +
            "        AND tl.gid = #{param.gid} " +
            "        AND tl.enable_status = #{param.enableStatus} " +
            "        AND tl.del_flag = '0' " +
            "    GROUP BY " +
            "        tlal.user " +
            ") AS user_counts;")
    HashMap<String,Object> findUvTypeCntByShortLink(@Param("param")ShortLinkStatsReqDTO requestParam);
}
