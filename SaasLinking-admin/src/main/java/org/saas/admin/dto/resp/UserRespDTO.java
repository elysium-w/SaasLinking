package org.saas.admin.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.saas.admin.common.serialize.IdCardDesensitizationSerializer;
import org.saas.admin.common.serialize.PhoneDesensitizationSerializer;
import org.saas.admin.common.serialize.RealNameDesensitizationSerializer;

/**
 * 
 */
@Data
public class UserRespDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    @JsonSerialize(using = RealNameDesensitizationSerializer.class)
    private String realName;

    /**
     * 手机号
     */
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

}
