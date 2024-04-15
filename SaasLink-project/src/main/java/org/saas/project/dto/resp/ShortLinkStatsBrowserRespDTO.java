package org.saas.project.dto.resp;

import com.oracle.truffle.api.dsl.NodeChild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

/**
 * 短链接浏览器访问详情监控响应参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkStatsBrowserRespDTO {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 占比
     */
    private Double ratio;
}
