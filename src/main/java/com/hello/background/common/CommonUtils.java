package com.hello.background.common;

import com.hello.background.domain.Candidate;
import com.hello.background.utils.DateTimeUtil;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

/**
 * @author wuketao
 * @date 2021/7/18
 * @Description
 */
public class CommonUtils {

    /**
     * 计算年龄
     *
     * @param candidate
     * @return
     */
    public static Candidate calcAge(Candidate candidate) {
        if (!StringUtils.isEmpty(candidate.getBirthDay())) {
            try {
                LocalDate ld = DateTimeUtil.convertToLocalDate(candidate.getBirthDay());
                if (null == ld) {
                    return candidate;
                }
                LocalDate now = LocalDate.now();
                int year = now.getYear() - ld.getYear();
                int month = now.getMonthValue() - ld.getMonthValue();
                candidate.setAge(month > 0 ? year + 1 : year);
            } catch (Exception ex) {
            }
        }
        return candidate;
    }
}
