package com.hello.background.service;

import com.hello.background.domain.Label;
import com.hello.background.repository.LabelRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.LabelVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 标签服务类
 *
 * @author wuketao
 * @date 2023/4/29
 * @Description
 */
@Transactional
@Slf4j
@Service
public class LabelService {
    @Autowired
    private LabelRepository labelRepository;

    /**
     * 保存标签
     *
     * @param name
     */
    public void save(String name) {
        /**
         * 首先通过名称查重
         */
        List<Label> labelList = labelRepository.findByName(name);
        /**
         * 避免名称相同的标签
         */
        if (labelList.size() == 0) {
            Label label = new Label();
            label.setName(name);
            labelRepository.save(label);
        }
    }

    /**
     * 获取全部标签
     *
     * @return
     */
    public List<LabelVO> findAll() {
        List<LabelVO> labelVOList = new ArrayList<>();
        Iterable<Label> all = labelRepository.findAll();
        Iterator<Label> iterator = all.iterator();
        while (iterator.hasNext()) {
            labelVOList.add(TransferUtil.transferTo(iterator.next(), LabelVO.class));
        }
        return labelVOList;
    }

    /**
     * 通过名称删除标签
     *
     * @param name
     */
    public void deleteByName(String name) {
        labelRepository.deleteByName(name);
    }

    /**
     * 先删除，然后获取全部标签
     *
     * @param name
     * @return
     */
    public List<LabelVO> deleteThenFindAllName(String name) {
        labelRepository.deleteByName(name);
        return findAll();
    }
}
