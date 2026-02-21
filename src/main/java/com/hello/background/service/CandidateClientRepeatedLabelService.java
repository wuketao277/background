package com.hello.background.service;

import com.hello.background.domain.CandidateClientRepeatedLabel;
import com.hello.background.repository.CandidateClientRepeatedLabelRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CandidateClientRepeatedLabelVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 候选人客户重复标签服务类
 *
 * @author wuketao
 * @date 2026/2/20
 * @Description
 */
@Transactional
@Slf4j
@Service
public class CandidateClientRepeatedLabelService {
    @Autowired
    private CandidateClientRepeatedLabelRepository labelRepository;

    /**
     * 保存标签
     *
     * @param name
     */
    public void save(String name) {
        /**
         * 首先通过名称查重
         */
        List<CandidateClientRepeatedLabel> labelList = labelRepository.findByName(name);
        /**
         * 避免名称相同的标签
         */
        if (labelList.size() == 0) {
            CandidateClientRepeatedLabel label = new CandidateClientRepeatedLabel();
            label.setName(name);
            labelRepository.save(label);
        }
    }

    /**
     * 获取全部标签
     *
     * @return
     */
    public List<CandidateClientRepeatedLabelVO> findAll() {
        List<CandidateClientRepeatedLabelVO> labelVOList = new ArrayList<>();
        Iterable<CandidateClientRepeatedLabel> all = labelRepository.findAll();
        Iterator<CandidateClientRepeatedLabel> iterator = all.iterator();
        while (iterator.hasNext()) {
            labelVOList.add(TransferUtil.transferTo(iterator.next(), CandidateClientRepeatedLabelVO.class));
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
    public List<CandidateClientRepeatedLabelVO> deleteThenFindAllName(String name) {
        labelRepository.deleteByName(name);
        return findAll();
    }
}
