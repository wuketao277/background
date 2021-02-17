package com.hello.background.service;

import com.hello.background.domain.CandidateForCase;
import com.hello.background.repository.CandidateForCaseRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CandidateForCaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 候选人与职位关系表 服务类
 *
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Transactional
@Slf4j
@Service
public class CandidateForCaseService {

    @Autowired
    private CandidateForCaseRepository candidateForCaseRepository;

    /**
     * 保存候选人
     *
     * @param vo
     * @return
     */
    public CandidateForCaseVO save(CandidateForCaseVO vo) {
        CandidateForCase candidateForCase = TransferUtil.transferTo(vo, CandidateForCase.class);
        return TransferUtil.transferTo(candidateForCaseRepository.save(candidateForCase), CandidateForCaseVO.class);
    }


    /**
     * 通过id删除，后续人与职位关联关系
     *
     * @param id
     */
    public void deleteById(Integer id) {
        candidateForCaseRepository.deleteById(id);
    }

    /**
     * 更新状态
     *
     * @param id        主键
     * @param newStatus 新状态
     * @return 更新是否成功
     */
    public boolean updateStatus(Integer id, String newStatus) {
        boolean flag = false;
        Optional<CandidateForCase> candidateForCaseOptional = candidateForCaseRepository.findById(id);
        if (candidateForCaseOptional.isPresent()) {
            CandidateForCase candidateForCase = candidateForCaseOptional.get();
            candidateForCase.setStatus(newStatus);
            candidateForCaseRepository.save(candidateForCase);
            flag = true;
        }
        return flag;
    }

    /**
     * 通过职位id获取所有职位推荐候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    public List<CandidateForCaseVO> findByCaseId(Integer caseId) {
        List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCaseId(caseId);
        return candidateForCaseList.stream().map(x -> TransferUtil.transferTo(x, CandidateForCaseVO.class)).collect(Collectors.toList());
    }
}
