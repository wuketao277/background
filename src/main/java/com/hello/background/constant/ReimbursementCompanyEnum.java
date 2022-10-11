package com.hello.background.constant;

/**
 * 报销公司
 *
 * @author wuketao
 * @date 2022/10/11
 * @Description
 */
public enum ReimbursementCompanyEnum {
    Shanghaihailuorencaifuwu("上海海罗人才服务有限公司"),
    Shanghaihailuorencaikeji("上海海罗人才科技有限公司"),
    Shenyanghailuorencaikeji("沈阳海罗人才服务有限公司");

    private String name;

    ReimbursementCompanyEnum(String _name) {
        this.name = _name;
    }
}
