package org.saas.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.saas.project.common.database.BaseDO;

import java.util.Date;

/**
 * 短链接用户操作系统访问实体
 */
@TableName("t_link_os_stats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkOSStatsDO extends BaseDO {
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
     * 操作系统
     */
    private String os;
}
