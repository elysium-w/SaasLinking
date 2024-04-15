package org.saas.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.saas.project.dao.entity.LinkAccessLogsDO;

/**
 * 短链接记录访问请求参数
 */
@Data
public class ShortLinkGroupStatsRecordDTO extends Page<LinkAccessLogsDO> {
}
