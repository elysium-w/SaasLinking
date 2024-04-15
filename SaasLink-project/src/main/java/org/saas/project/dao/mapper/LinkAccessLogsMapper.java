package org.saas.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.saas.project.dao.entity.LinkAccessLogsDO;
import org.saas.project.dao.entity.LinkAccessStatsDO;

/**
 *访问日志持久层
 */
public interface LinkAccessLogsMapper extends BaseMapper<LinkAccessLogsDO> {


}
