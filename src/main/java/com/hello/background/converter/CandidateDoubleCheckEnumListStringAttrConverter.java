package com.hello.background.converter;

import com.alibaba.fastjson.JSONArray;
import com.hello.background.constant.CandidateDoubleCheckEnum;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * List<RoleEnum> 到 String的转换器
 *
 * @author wuketao
 * @date 2022/9/27
 * @Description
 */
public class CandidateDoubleCheckEnumListStringAttrConverter implements AttributeConverter<List<CandidateDoubleCheckEnum>, String>, Serializable {
    @Override
    public String convertToDatabaseColumn(List<CandidateDoubleCheckEnum> roleEnums) {
        return JSONArray.toJSONString(roleEnums);
    }

    @Override
    public List<CandidateDoubleCheckEnum> convertToEntityAttribute(String s) {
        if (Strings.isBlank(s)) {
            return new ArrayList<>();
        }
        return JSONArray.parseArray(s, CandidateDoubleCheckEnum.class);
    }
}
