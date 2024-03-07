package org.saas.admin.common.serialize;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
/**
 * 身份证号脱敏反序列化
 */
public class IdCardDesensitizationSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long idCard, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String idDesensitization = DesensitizedUtil.idCardNum(String.valueOf(idCard),4,0);
        jsonGenerator.writeString(idDesensitization);
    }
}
