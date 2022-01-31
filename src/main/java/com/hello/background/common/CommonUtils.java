package com.hello.background.common;

import com.hello.background.domain.Candidate;
import com.hello.background.utils.DateTimeUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * 拆分搜索关键词
     *
     * @param search
     * @return
     */
    public static List<String> splitSearchWord(String search) {
        if (StringUtils.isEmpty(search)) {
            return Collections.EMPTY_LIST;
        }
        List<String> list = new ArrayList<>();
        String[] array = search.split("and");
        for (int i = 0; i < array.length; i++) {
            if (Strings.isNotBlank(array[i].trim())) {
                String[] array2 = array[i].trim().split("AND");
                for (int j = 0; j < array2.length; j++) {
                    if (Strings.isNotBlank(array2[j].trim())) {
                        list.add(array2[j].trim());
                    }
                }
            }
        }
        return list;
    }

}
