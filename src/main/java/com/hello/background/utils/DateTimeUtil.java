package com.hello.background.utils;

import org.apache.logging.log4j.util.Strings;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author wuketao
 * @date 2021/5/29
 * @Description
 */
public class DateTimeUtil {
    private static final String sf1 = "\\d{4}[.]{1}\\d{1,2}[.]{1}\\d{1,2}";
    private static final String sf2 = "\\d{4}-\\d{1,2}-\\d{1,2}";
    private static final String sf3 = "\\d{4}/\\d{1,2}/\\d{1,2}";
    /**
     * 开始日期
     */
    public static final Date startDate = localDate2Date(LocalDate.of(1949, 10, 1));
    /**
     * 结束日期
     */
    public static final Date endDate = localDate2Date(LocalDate.of(9999, 12, 31));

    /**
     * 转换为日期
     *
     * @param content
     * @return
     */
    public static LocalDate convertToLocalDate(String content) {
        if (Strings.isBlank(content)) {
            return null;
        }
        String[] parts = new String[3];
        if (Pattern.matches(sf1, content)) {
            parts = content.split(".");
        } else if (Pattern.matches(sf2, content)) {
            parts = content.split("-");
        } else if (Pattern.matches(sf3, content)) {
            parts = content.split("/");
        } else {
            return null;
        }
        content = String.format("%4d-%02d-%02d", Integer.valueOf(parts[0]), Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
        return LocalDate.parse(content);
    }

    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getTimeStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 24HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public static Date localDate2Date(LocalDate ld) {
        if (null == ld) {
            return null;
        }
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate date2LocalDate(Date date) {
        if (null != date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
