package com.hello.background.constant;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 假期常量类
 *
 * @author wuketao
 * @date 2023/4/21
 * @Description
 */
@Data
public class HolidayConstants {
    /**
     * 2023节假日
     */
    public static List<String> holiday2023 = Arrays.asList("1月2日", "1月3日", "1月21日", "1月22日", "1月23日", "1月24日", "1月25日", "1月26日", "1月27日", "4月5日", "4月29日", "4月30日", "5月1日", "5月2日", "5月3日", "6月22日", "6月23日", "6月24日", "9月29日", "9月29日", "9月30日", "10月1日", "10月2日", "10月3日", "10月4日", "10月5日", "10月6日");
    /**
     * 2023周末调休
     */
    public static List<String> workday2023 = Arrays.asList("1月28日", "1月29日", "4月23日", "5月6日", "6月25日", "10月7日", "10月8日");
    /**
     * 2024节假日
     */
    public static List<String> holiday2024 = Arrays.asList("1月1日", "2月10日", "2月11日", "2月12日", "2月13日", "2月14日", "2月15日"
            , "2月16日", "2月17日", "4月4日", "4月5日", "4月6日", "5月1日", "5月2日", "5月3日", "5月4日", "5月5日", "6月10日", "9月15日"
            , "9月16日", "9月17日", "10月1日", "10月2日", "10月3日", "10月4日", "10月5日", "10月6日", "10月7日");
    /**
     * 2024周末调休
     */
    public static List<String> workday2024 = Arrays.asList("2月4日", "2月18日", "4月7日", "4月28日", "5月11日", "9月14日", "9月29日", "10月12日");
}
