package com.hello.background.service;

import com.hello.background.domain.CandidateForCase;
import com.hello.background.repository.CandidateForCaseRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CandidateForCaseVO;
import com.hello.background.vo.ClientVO;
import com.hello.background.vo.CopyFromOldCaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Autowired
    private ClientService clientService;

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
     * 从旧职位拷贝候选人到新职位
     *
     * @param vo
     */
    public void copyFromOldCase(CopyFromOldCaseVO vo) {
        List<CandidateForCase> oldCaseCandidateList = candidateForCaseRepository.findByCaseId(vo.getOldCaseId());
        List<CandidateForCase> curCaseCandidateList = candidateForCaseRepository.findByCaseId(vo.getCurCaseId());
        for (CandidateForCase old : oldCaseCandidateList) {
            if (curCaseCandidateList.stream().filter(x -> old.getCandidateId().equals(x.getCandidateId())).count() == 0) {
                CandidateForCase record = new CandidateForCase();
                BeanUtils.copyProperties(old, record);
                record.setId(null);
                record.setCaseId(vo.getCurCaseId());
                record.setCreateTime(LocalDateTime.now());
                candidateForCaseRepository.save(record);
            }
        }
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
        return convertToVOList(candidateForCaseList);
    }

    /**
     * 通过候选人id获取所有职位推荐候选人信息
     *
     * @param candidateId
     * @return
     */
    public List<CandidateForCaseVO> findByCandidateId(Integer candidateId) {
        List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCandidateId(candidateId);
        return convertToVOList(candidateForCaseList);
    }

    private List<CandidateForCaseVO> convertToVOList(List<CandidateForCase> candidateForCaseList) {
        List<CandidateForCaseVO> candidateForCaseVOList = candidateForCaseList.stream().map(x -> TransferUtil.transferTo(x, CandidateForCaseVO.class)).collect(Collectors.toList());
        List<ClientVO> clientVOList = clientService.findAll();
        candidateForCaseVOList.forEach(cc -> {
            Optional<ClientVO> firstClientVOOptional = clientVOList.stream().filter(clientVO -> clientVO.getId().equals(cc.getClientId())).findFirst();
            if (firstClientVOOptional.isPresent()) {
                cc.setClientName(firstClientVOOptional.get().getChineseName());
            }
        });
        return candidateForCaseVOList;
    }


    /**
     * 通过候选人id、职位id查询
     *
     * @param candidateId
     * @param caseId
     * @return
     */
    public List<CandidateForCase> findByCandidateIdAndCaseId(Integer candidateId, Integer caseId) {
        return candidateForCaseRepository.findByCandidateIdAndCaseId(candidateId, caseId);
    }
}
