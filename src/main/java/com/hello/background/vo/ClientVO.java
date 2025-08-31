package com.hello.background.vo;

import com.hello.background.domain.Client;
import com.hello.background.domain.ClientExt;
import com.hello.background.utils.TransferUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * 地址
     */
    private String address;

    /**
     * 客户信息
     */
    private String information;

    /**
     * 推荐流程
     */
    private String recommendationProcess;

    /**
     * 查重要求
     */
    private String duplicateCheck;

    /**
     * 简历标准
     */
    private String resumeStandard;

    /**
     * 薪资架构
     */
    private String salaryStructure;

    /**
     * 推荐理由
     */
    private String recommendationReason;

    /**
     * 面试准备
     */
    private String interviewPrepare;

    /**
     * 公司卖点
     */
    private String sellingPoint;

    /**
     * 特别说明
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

    /**
     * 兼职
     */
    private List<String> parttimers = new ArrayList<>();

    /**
     * 通过vo对象转换domain对象
     *
     * @return
     */
    public Client toClient() {
        Client client = TransferUtil.transferTo(this, Client.class);
        if (null != this.getParttimers() && !this.getParttimers().isEmpty()) {
            client.setParttimers(Strings.join(this.getParttimers(), ','));
        }
        return client;
    }

    /**
     * 通过客户基本信息和客户扩展信息
     *
     * @param client
     * @param ext
     */
    public ClientVO(Client client, ClientExt ext) {
        TransferUtil.transfer(client, this);
        if (Strings.isNotBlank(client.getParttimers())) {
            this.setParttimers(Arrays.asList(client.getParttimers().split(",")));
        }
        TransferUtil.transfer(ext, this);
    }
}
