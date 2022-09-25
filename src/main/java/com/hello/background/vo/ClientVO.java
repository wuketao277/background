package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 客户
 *
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 中文名
     */
    private String chineseName;

    /**
     * 英文名
     */
    private String englishName;

    /**
     * 客户备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 发票联系信息
     */
    private String invoiceContact;

    /**
     * 发票备注
     */
    private String invoiceRemark;
}
