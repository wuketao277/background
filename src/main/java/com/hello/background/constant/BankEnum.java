package com.hello.background.constant;

/**
 * 银行枚举
 *
 * @author wuketao
 * @date 2022/9/14
 * @Description
 */
public enum BankEnum {
    ICBC("ICBC", "工商银行"),
    ABC("ABC", "农业银行"),
    CMB("CMB", "招商银行"),
    CCB("CCB", "建设银行"),
    BCM("BCM", "交通银行"),
    PAB("PAB", "平安银行"),
    CEB("CEB", "光大银行"),
    BOC("BOC", "中国银行"),
    SPDB("SPDB", "浦发银行"),
    CITIC("CITIC", "中信银行"),
    BOS("BOS", "上海银行"),
    CMBC("CMBC", "民生银行"),
    CIB("CIB", "兴业银行"),
    HB("HB", "华夏银行");

    private String code;
    private String name;

    BankEnum(String _code, String _name) {
        this.code = _code;
        this.name = _name;
    }
}
