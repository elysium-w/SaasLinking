package org.saas.project.tookit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import static org.saas.project.common.constant.ShortLinkConstant.DEFAULT_CACHE_VALID_TIME;

/**
 * 短链接工具类
 */
public class LinkUtil {
    /**
     * 获取短链接有限时间
     * @param validDate 有效期时间
     * @return 有效期时间戳
     */
    public static long getLinkCacheValidDate(Date validDate){
        return Optional.ofNullable(validDate)
                .map(each -> DateUtil.between(new Date(),each, DateUnit.MS))
                .orElse(DEFAULT_CACHE_VALID_TIME);
    }

    /**
     * 获取用户真实IP
     * @param request 网络报文
     * @return IP地址
     */
    public static  String getActualIp(HttpServletRequest request){
        String[] headerNames = {"X-Forwarded-For","Proxy-Client-IP","WL-Proxy-Client-IP","HTTP_CLIENT_IP","HTTP_X_FORWARDED_FOR"};
        for (String header : headerNames){
            String ipAddress = request.getHeader(header);
            if (ipAddress != null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)){
                return ipAddress;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取用户操作系统
     * @param request 网络报文
     * @return 用户操作系统
     */
    public static String getOS(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        if(userAgent.contains("windows")){
            return "Windows";
        } else if (userAgent.contains("mac")) {
            return "Mac OS";
        } else if (userAgent.contains("linux")){
            return "Linux";
        }else if (userAgent.toLowerCase().contains("android")) {
            return "Android";
        } else if (userAgent.toLowerCase().contains("iphone") || userAgent.toLowerCase().contains("ipad")) {
            return "iOS";
        } else {
            return "Unknown";
        }
    }
}
