package org.saas.project.dao.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.saas.project.common.database.BaseDO;

import java.util.Date;

/**
 * 短链接用户浏览器访问实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_link_browser_stats")
public class LinkBrowserStatsDO extends BaseDO {

    /**
     * id
     */
    private Long id;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 日期
     */
    private Date date;

    /**
     * 访问量
     */
    private Integer cnt;

    /**
     * 浏览器
     */
    private String browser;
}
