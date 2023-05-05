package com.hello.background.constant;

/**
 * 报销公司
 *
 * @author wuketao
 * @date 2022/10/11
 * @Description
 */
public enum CompanyEnum {
    Shanghaihailuorencaifuwu("上海海罗人才服务有限公司"),
    Shanghaihailuorencaikeji("上海海罗人才科技有限公司"),
    Shenyanghailuorencaifuwu("沈阳海罗人才服务有限公司"),
    Nanjinghailuorencaifuwu("南京海罗人才服务有限公司"),
    Wuhanhailuorencaifuwu("武汉海罗人才服务有限公司");

    private String name;

    public String getName() {
        return name;
    }

    CompanyEnum(String _name) {
        this.name = _name;
    }
}
