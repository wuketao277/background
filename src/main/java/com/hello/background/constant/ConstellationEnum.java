package com.hello.background.constant;

/**
 * 星座枚举
 *
 * @author wuketao
 * @date 2022/9/14
 * @Description
 */
public enum ConstellationEnum {
    SHUIPING("SHUIPING", "水瓶"),
    SHUANGYU("SHUANGYU", "双鱼"),
    BAIYANG("BAIYANG", "白羊"),
    JINNIU("JINNIU", "金牛"),
    SHUANGZI("SHUANGZI", "双子"),
    JUXIE("JUXIE", "巨蟹"),
    SHIZI("SHIZI", "狮子"),
    CHUNV("CHUNV", "处女"),
    TIANPING("TIANPING", "天平"),
    TIANXIE("TIANXIE", "天蝎"),
    SHESHOU("SHESHOU", "射手"),
    MOJIE("MOJIE", "摩羯");
    /**
     * 代码
     */
    private String code;
    /**
     * 描述
     */
    private String describe;

    ConstellationEnum(String _code, String _describe) {
        this.code = _code;
        this.describe = _describe;
    }
}
