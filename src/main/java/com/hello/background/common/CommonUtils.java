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

    public static String getClientShortName(String clientName) {
        switch (clientName) {
            // 一汽集团相关公司
            case "中国第一汽车集团有限公司盐城分公司":
                clientName = "一汽盐城";
                break;
            case "一汽铸造有限公司":
                clientName = "一汽铸造";
                break;
            case "中国第一汽车股份有限公司动能分公司":
                clientName = "一汽动能";
                break;
            case "一汽丰田汽车有限公司":
                clientName = "一丰";
                break;
            case "一汽丰田汽车销售有限公司":
                clientName = "一丰销售";
                break;
            case "启明信息技术股份有限公司":
                clientName = "一汽启明";
                break;
            case "一汽汽车金融有限公司-一汽金融附属公司":
                clientName = "一汽金融";
                break;
            case "一汽资本控股有限公司-一汽金融附属公司":
                clientName = "一汽资本";
                break;
            case "一汽租赁有限公司-一汽金融附属公司":
                clientName = "一汽租赁";
                break;
            case "一汽财务有限公司-一汽金融附属公司":
                clientName = "一汽财务";
                break;
            case "信达一汽商业保理有限公司-一汽金融附属公司":
                clientName = "一汽信达";
                break;
            case "鑫安汽车保险股份有限公司-一汽金融附属公司":
                clientName = "一汽鑫安";
                break;
            case "中国第一汽车集团进出口有限公司":
                clientName = "一汽进出口";
                break;
            case "一汽富华生态有限公司":
                clientName = "一汽富华";
                break;
            case "一汽股权投资（天津）有限公司":
                clientName = "一汽股权";
                break;
            case "一汽解放汽车有限公司":
                clientName = "一汽解放";
                break;
            case "一汽物流有限公司":
                clientName = "一汽物流";
                break;
            case "一汽大众":
                clientName = "一汽大众";
                break;
            case "一汽奔腾汽车股份有限公司":
                clientName = "一汽奔腾";
                break;
            case "中国第一汽车股份有限公司":
                clientName = "中国一汽";
                break;

            // 宝马相关公司
            case "宝马（中国）汽车贸易有限公司":
                clientName = "宝马贸易";
                break;
            case "宝马（中国）投资有限公司":
                clientName = "宝马投资";
                break;
            case "华晨宝马汽车有限公司北京分公司":
                clientName = "华晨宝马北京";
                break;
            case "华晨宝马汽车有限公司沈阳":
                clientName = "华晨宝马沈阳";
                break;
            case "宝马汽车金融（中国）有限公司":
                clientName = "宝马金融";
                break;
            case "宝马（中国）服务有限公司":
                clientName = "宝马中国";
                break;

            // 沃尔沃相关公司
            case "大庆沃尔沃汽车制造有限公司":
                clientName = "大庆沃尔沃";
                break;
            case "沃尔沃亚太-沃尔沃汽车（亚太）投资控股有限公司":
                clientName = "沃尔沃亚太投资";
                break;
            case "沃尔沃亚太-沃尔沃汽车技术（上海）有限公司":
                clientName = "沃尔沃亚太";
                break;
            case "沃尔沃汽车销售（上海）有限公司":
                clientName = "沃尔沃销售";
                break;

            // 理想汽车相关公司
            case "理想-斯科半导体（苏州）":
                clientName = "理想斯科";
                break;
            case "理想-汇想常想新晨":
                clientName = "理想汇想常想新晨";
                break;
            case "北京罗克维尔斯科技有限公司/理想汽车/北京车和家信息技术有限公司":
                clientName = "理想";
                break;

            // 丰田相关公司
            case "丰田汽车（中国）投资有限公司+雷克萨斯":
                clientName = "丰田汽车";
                break;

            // 福特相关公司
            case "福特汽车工程研究（南京）有限公司":
                clientName = "福特南京";
                break;
            case "福特汽车（中国）有限公司":
                clientName = "福特汽车";
                break;
            case "福特汽车金融（中国）有限公司":
                clientName = "福特金融";
                break;

            // 三菱相关公司
            case "上海三菱电梯有限公司":
                clientName = "三菱";
                break;
            case "上海三菱电梯有限公司安徽分公司":
                clientName = "三菱安徽";
                break;

            // 领悦相关公司
            case "领悦数字信息技术有限公司":
                clientName = "领悦";
                break;
            case "领悦数字信息技术有限公司南京分公司":
                clientName = "领悦南京";
                break;

            // 其他独立公司
            case "恩德斯豪斯分析仪器（苏州）有限公司":
                clientName = "恩德斯豪斯";
                break;
            case "无锡先导智能装备股份有限公司":
                clientName = "先导";
                break;
            case "环浔科技（苏州）有限公司":
                clientName = "环浔";
                break;
            case "奇瑞海外实业投资有限公司":
                clientName = "奇瑞海外";
                break;
            case "红旗投资管理（吉林）有限公司":
                clientName = "红旗投资";
                break;
            case "上海重塑能源集团股份有限公司":
                clientName = "上海重塑";
                break;
            case "亚欧汽车制造（台州）有限公司":
                clientName = "亚欧汽车";
                break;
            case "北京励鼎汽车销售有限公司":
                clientName = "北京励鼎";
                break;
            case "先锋国际融资租赁有限公司":
                clientName = "先锋租赁";
                break;
            case "武汉东方骏驰精密制造有限公司":
                clientName = "东方骏驰";
                break;
            case "上海珀懿电子商务有限公司":
                clientName = "珀懿";
                break;
            case "溯高美索克曼":
                clientName = "溯高美索克曼";
                break;
            case "常州悦亿网络信息技术有限公司":
                clientName = "常州悦亿";
                break;
            case "喜利得（中国）商贸有限公司":
                clientName = "喜利得";
                break;
        }
        return clientName;
    }
}
