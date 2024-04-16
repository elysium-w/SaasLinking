package org.saas.project.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import org.saas.project.dao.entity.*;
import org.saas.project.dao.mapper.*;
import org.saas.project.dto.req.ShortLinkStatsReqDTO;
import org.saas.project.dto.resp.*;
import org.saas.project.service.ShortLinkStatsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 短链接监控接口实现层
 */
@Service
@RequiredArgsConstructor
public class ShortLinkStatsServiceImpl implements ShortLinkStatsService {
    private final LinkAccessStatsMapper linkAccessStatsMapper;
    private final LinkAccessLogsMapper linkAccessLogsMapper;
    private final LinkLocaleStatsMapper linkLocaleStatsMapper;
    private final LinkBrowserStatsMapper linkBrowserStatsMapper;
    private final LinkOSStatsMapper linkOSStatsMapper;
    private final LinkDeviceStatsMapper linkDeviceStatsMapper;
    private final LinkNetworkStatsMapper linkNetworkStatsMapper;
    @Override
    public ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam) {
//      判断是否存在访问记录
        List<LinkAccessStatsDO> listStatsByShortLink = linkAccessStatsMapper.listStatsByShortLink(requestParam);
        if (CollUtil.isEmpty(listStatsByShortLink)){
            return null;
        }
//        获取基础访问数据
        LinkAccessStatsDO linkAccessStatsDO = linkAccessLogsMapper.findPvUvUidStatsByShortLink(requestParam);
//        基础访问详情
        List<ShortLinkStatsAccessDailyRespDTO> daily = new ArrayList<>();
        List<String> rangeDates = DateUtil.rangeToList(DateUtil.parse(requestParam.getStartDate()), DateUtil.parse(requestParam.getEndDate()), DateField.DAY_OF_MONTH).stream()
                .map(DateUtil::formatDate)
                .toList();
        rangeDates.forEach(each -> listStatsByShortLink.stream()
                .filter(item -> Objects.equals(each,DateUtil.formatDate(item.getDate())))
                .findFirst()
                .ifPresentOrElse(item -> {
                    ShortLinkStatsAccessDailyRespDTO accessDailyRespDTO = ShortLinkStatsAccessDailyRespDTO.builder()
                            .date(each)
                            .pv(item.getPv())
                            .uv(item.getUv())
                            .uip(item.getUip())
                            .build();
                    daily.add(accessDailyRespDTO);
                },() -> {
                    ShortLinkStatsAccessDailyRespDTO accessDailyRespDTO = ShortLinkStatsAccessDailyRespDTO.builder()
                            .date(each)
                            .pv(0)
                            .uv(0)
                            .uip(0)
                            .build();
                    daily.add(accessDailyRespDTO);
                }));
//        地区访问详情
        List<ShortLinkStatsLocaleCNRespDTO> localeCnStats = new ArrayList<>();
        List<LinkLocaleStatsDO> listLocaleByShortLink = linkLocaleStatsMapper.listLocaleByShortLink(requestParam);
        int localeCnSum = listLocaleByShortLink.stream()
                .mapToInt(LinkLocaleStatsDO::getCnt)
                .sum();
        listLocaleByShortLink.forEach(each -> {
            double ratio = (double) each.getCnt() / localeCnSum;
            double actualRatio = Math.round(ratio * 100.0) / 100.0;
            ShortLinkStatsLocaleCNRespDTO localeCNRespDTO = ShortLinkStatsLocaleCNRespDTO.builder()
                    .cnt(each.getCnt())
                    .locale(each.getProvince())
                    .ratio(actualRatio)
                    .build();
        });
//        一小时访问
        List<Integer> hourStats = new ArrayList<>();
        List<LinkAccessStatsDO> listHourStatsByShortLink = linkAccessStatsMapper.listStatsByShortLink(requestParam);
        for (int i = 0; i < 24; i++) {
            AtomicInteger hour = new AtomicInteger(i);
            int hourCnt = listHourStatsByShortLink.stream()
                    .filter(each -> Objects.equals(each.getHour(),hour.get()))
                    .findFirst()
                    .map(LinkAccessStatsDO::getPv)
                    .orElse(0);
            hourStats.add(hourCnt);
        }
//        高频访问IP
        List<Integer> weekdayStats = new ArrayList<>();
        List<LinkAccessStatsDO> listWeekdayStatsByShortLink = linkAccessStatsMapper.listWeekdayStatsBySHortLink(requestParam);
        for (int i = 1; i < 8; i++) {
            AtomicInteger weekdays = new AtomicInteger(i);
            int weekdayCnt = listWeekdayStatsByShortLink.stream()
                    .filter(each -> Objects.equals(each.getWeekday(),weekdays.get()))
                    .findFirst()
                    .map(LinkAccessStatsDO::getPv)
                    .orElse(0);
            weekdayStats.add(weekdayCnt);
        }
//        访问浏览器类型详情
        List<ShortLinkStatsBrowserRespDTO> browserStats = new ArrayList<>();
        List<HashMap<String,Object>> listBrowserStatsByShortLink = linkBrowserStatsMapper.listBrowserStatsByShortLink(requestParam);
//        流式处理列表元素
        int browseSum = listBrowserStatsByShortLink.stream()
                .mapToInt(each -> Integer.parseInt(each.get("count").toString()))
                .sum();
        listBrowserStatsByShortLink.forEach(each -> {
            double ratio = (double) Integer.parseInt(each.get("count").toString()) / browseSum;
            double actualRatio = Math.round(ratio * 100.0) / 100.0;
        });

//        操作系统类型详情
        List<ShortLinkStatsOSRespDTO> OSStats = new ArrayList<>();

//        访客类型类型详情
//        访问设备类型详情
//        访问网络类型详情

        return ShortLinkStatsRespDTO.builder()

                .build();
    }
}
