package com.hello.background.converter;

import com.alibaba.fastjson.JSONArray;
import com.hello.background.constant.JobTypeEnum;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * List<JobTypeEnum> 到 String的转换器
 *
 * @author wuketao
 * @date 2023/1/9
 * @Description
 */
public class JobTypeEnumListStringAttrConverter implements AttributeConverter<List<JobTypeEnum>, String>, Serializable {
    @Override
    public String convertToDatabaseColumn(List<JobTypeEnum> jobTypeEnums) {
        return JSONArray.toJSONString(jobTypeEnums);
    }

    @Override
    public List<JobTypeEnum> convertToEntityAttribute(String s) {
        if (Strings.isBlank(s)) {
            return new ArrayList<>();
        }
        return JSONArray.parseArray(s, JobTypeEnum.class);
    }
}
