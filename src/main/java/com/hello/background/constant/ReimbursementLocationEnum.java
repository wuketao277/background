package com.hello.background.constant;

/**
 * 报销地点
 *
 * @author wuketao
 * @date 2022/10/11
 * @Description
 */
public enum ReimbursementLocationEnum {
    Shanghai("上海"),
    Beijing("北京"),
    Shenyang("沈阳"),
    Nanjing("南京"),
    Wuhan("武汉"),
    Enshi("恩施");

    private String name;

    public String getName() {
        return this.name;
    }

    ReimbursementLocationEnum(String _name) {
        this.name = _name;
    }
}
