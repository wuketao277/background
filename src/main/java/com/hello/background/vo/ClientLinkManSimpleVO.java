package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientLinkManSimpleVO {
    /**
     * HRid
     */
    private Integer id;

    /**
     * HR英文名-中文名
     */
    private String name;

    /**
     * 中文名
     */
    private String chineseName;

    /**
     * 联系人英文名
     */
    private String englishName;
}
