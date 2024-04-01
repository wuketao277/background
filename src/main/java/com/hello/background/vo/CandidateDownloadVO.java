package com.hello.background.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hello.background.domain.Candidate;
import com.hello.background.utils.DateTimeUtil;
import com.hello.background.utils.TransferUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 候选人
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDownloadVO {
    /**
     * 主键id
     */
    @ExcelProperty(value = "序号")
    private Integer id;

    /**
     * 中文名字
     */
    @ExcelProperty(value = "中文名")
    private String chineseName;

    /**
     * 英文名字
     */
    @ExcelProperty(value = "英文名")
    private String englishName;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    private Integer age;

    /**
     * 生日
     */
    @ExcelProperty(value = "生日")
    private String birthDay;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话")
    private String phoneNo;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private String gender;

    /**
     * 英语水平
     */
    @ExcelProperty(value = "英语水平")
    private String englishLevel;

    /**
     * 户籍地址
     */
    @ExcelProperty(value = "户籍地址")
    private String hometown;

    /**
     * 现地址
     */
    @ExcelProperty(value = "现地址")
    private String currentAddress;

    /**
     * 期望地址
     */
    @ExcelProperty(value = "期望地址")
    private String futureAddress;

    /**
     * 家庭情况
     */
    @ExcelProperty(value = "家庭情况")
    private String family;

    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称")
    private String companyName;

    /**
     * 现薪资
     */
    @ExcelProperty(value = "现薪资")
    private String currentMoney;

    /**
     * 期望薪资
     */
    @ExcelProperty(value = "期望薪资")
    private String futureMoney;

    /**
     * 公司架构
     */
    @ExcelProperty(value = "公司架构")
    private String companyStructure;

    /**
     * 学校名称
     */
    @ExcelProperty(value = "学校名称")
    private String schoolName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 候选人不匹配原因
     */
    @ExcelProperty(value = "不匹配原因")
    private String notMatchReason;

    /**
     * 候选人不匹配的详细原因
     */
    @ExcelProperty(value = "不匹配的详细原因")
    private String notMatchReasonDetail;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String createTime;

    public CandidateDownloadVO(Candidate candidate) {
        TransferUtil.transfer(candidate, this);
        this.gender = candidate.getGender().getDescribe();
        this.createTime = DateTimeUtil.getDateStr(candidate.getCreateTime());
        this.notMatchReason = candidate.getNotMatchReason().getName();
    }
}
