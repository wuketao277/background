package com.hello.background.domain;

import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.MyNewsVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 新闻领域对象
 *
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MyNews {

    /**
     * 新闻主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 新闻优先级，1~5，1最低，5最高
     */
    @Column(nullable = false)
    private String priority;

    /**
     * 新闻标题
     * 最多200个字符，不允许为空
     */
    @Column(length = 200, nullable = false)
    private String title;

    /**
     * 新闻内容
     * 最多4000个字符
     */
    @Column(length = 4000)
    private String content;

    /**
     * 新闻链接
     * 最多500个字符
     */
    @Column(length = 500)
    private String link;

    /**
     * 发布状态：true表示发布，false表示不发布
     */
    @Column
    private Boolean publish;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    @Column(length = 50)
    private String createUserId;

    /**
     * 转换为VO
     *
     * @return
     */
    public MyNewsVO toVO() {
        MyNewsVO vo = new MyNewsVO();
        TransferUtil.transfer(this, vo);
        return vo;
    }
}
