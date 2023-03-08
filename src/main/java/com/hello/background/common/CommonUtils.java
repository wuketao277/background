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
     * @param birthdayStr
     * @return
     */
    public static Integer calcAge(String birthdayStr) {
        if (!StringUtils.isEmpty(birthdayStr)) {
            try {
                LocalDate ldBirthday = DateTimeUtil.convertToLocalDate(birthdayStr);
                if (null != ldBirthday) {
                    LocalDate now = LocalDate.now();
                    int year = now.getYear() - ldBirthday.getYear();
                    int month = now.getMonthValue() - ldBirthday.getMonthValue();
                    int day = now.getDayOfMonth() - ldBirthday.getDayOfMonth();
                    if (month < 0) {
                        // 还没到生日月份，等于年差-1
                        return year - 1;
                    } else if (month == 0) {
                        if (day < 0) {
                            // 还没到生日天数，等于年差-1
                            return year - 1;
                        } else {
                            // 已经到了或过了生日天数，直接就等于年差
                            return year;
                        }
                    } else {
                        // 已经过了生日月份，直接就等于年差
                        return year;
                    }
                }
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 计算年龄
     *
     * @param candidate
     * @return
     */
    public static Candidate calcAge(Candidate candidate) {
        candidate.setAge(calcAge(candidate.getBirthDay()));
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
