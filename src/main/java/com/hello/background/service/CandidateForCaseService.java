package com.hello.background.service;

import com.hello.background.domain.Candidate;
import com.hello.background.domain.CandidateForCase;
import com.hello.background.domain.ClientCase;
import com.hello.background.repository.CandidateForCaseRepository;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.repository.CaseRepository;
import com.hello.background.utils.EasyExcelUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
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
    @Autowired
    private CommentService commentService;
    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * 保存候选人
     *
     * @param vo
     * @return
     */
    public CandidateForCaseVO save(CandidateForCaseVO vo) {
        CandidateForCase candidateForCase = TransferUtil.transferTo(vo, CandidateForCase.class);
        // 新添加的推荐，默认候选人是关注的
        candidateForCase.setAttention(true);
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
        ClientCase curCase = caseRepository.findById(vo.getCurCaseId()).get();
        for (CandidateForCase old : oldCaseCandidateList) {
            if (curCaseCandidateList.stream().filter(x -> old.getCandidateId().equals(x.getCandidateId())).count() == 0) {
                CandidateForCase record = new CandidateForCase();
                record.setCandidateId(old.getCandidateId());
                record.setChineseName(old.getChineseName());
                record.setEnglishName(old.getEnglishName());
                record.setClientId(curCase.getClientId());
                record.setCaseId(curCase.getId());
                record.setTitle(curCase.getTitle());
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
     * 通过职位id下载所有职位推荐候选人信息
     *
     * @param caseId
     * @param response
     */
    public void downloadCandidates(Integer caseId, HttpServletResponse response) {
        List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCaseId(caseId);
        List<CandidateDownloadVO> list = new ArrayList<>();
        for (CandidateForCase cc : candidateForCaseList) {
            Optional<Candidate> optionalCandidate = candidateRepository.findById(cc.getCandidateId());
            list.add(new CandidateDownloadVO(optionalCandidate.get(), true));
        }
        // 封装返回response
        try {
            EasyExcelUtil.downloadExcel(response, "候选人列表", null, list, CandidateDownloadVO.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 通过职位id获取所有职位推荐候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    public List<CandidateForCaseVO> findAttentionByCaseId(Integer caseId) {
        List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCaseIdAndAttention(caseId, true);
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
            // 将候选人最后的评论信息添加到返回数据中
            List<CommentVO> commentVOList = commentService.findAllByCandidateId(cc.getCandidateId());
            if (!CollectionUtils.isEmpty(commentVOList)) {
                commentVOList.sort(Comparator.comparing(CommentVO::getId).reversed());
                cc.setLatestCommentContent(commentVOList.get(0).getContent());
                cc.setLatestCommentInputtime(commentVOList.get(0).getInputTime());
                cc.setLatestCommentUsername(commentVOList.get(0).getUsername());
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

    /**
     * 通过id更新attention字段
     *
     * @param id
     * @param attention
     * @return
     */
    public void updateAttention(Integer id, Boolean attention) {
        CandidateForCase candidateForCase = candidateForCaseRepository.findById(id).get();
        candidateForCase.setAttention(attention);
        candidateForCaseRepository.save(candidateForCase);
    }

    /**
     * 更新候选人在该职位上的最新阶段
     *
     * @param candidateId
     * @param caseId
     * @param newPhase
     */
    public void updateLastPhase(Integer candidateId, Integer caseId, String newPhase) {
        // 只有有效的阶段才进行储存
        List<String> phaseList = Arrays.asList("IOI", "VI", "CVO"
                , "1st Interview", "2nd Interview", "3rd Interview", "4th Interview", "5th Interview", "6th Interview",
                "Final Interview", "Offer Signed", "On Board", "Invoice", "Payment", "END", "Successful");
        if (phaseList.contains(newPhase)) {
            // 通过候选人Id和职位Id，查询关联关系
            Optional<CandidateForCase> optional = candidateForCaseRepository.findByCandidateIdAndCaseId(candidateId, caseId).stream().findFirst();
            if (optional.isPresent()) {
                CandidateForCase candidateForCase = optional.get();
                if (newPhase.equals("IOI") || newPhase.equals("VI")) {
                    // IOI/VI只有在没有最新阶段时才进行更新更新数据
                    if (Strings.isBlank(candidateForCase.getLastPhase())) {
                        candidateForCase.setLastPhase(newPhase);
                        candidateForCaseRepository.save(candidateForCase);
                    }
                } else {
                    // 其他阶段都直接更新
                    candidateForCase.setLastPhase(newPhase);
                    candidateForCaseRepository.save(candidateForCase);
                }
            }
        }
    }

    /**
     * 更新候选人在该职位上的最远阶段
     *
     * @param candidateId
     * @param caseId
     * @param newPhase
     */
    public void updateFarthestPhase(Integer candidateId, Integer caseId, String newPhase) {
        // 只有有效的阶段才进行储存
        List<String> phaseList = Arrays.asList("CVO"
                , "1st Interview", "2nd Interview", "3rd Interview", "4th Interview", "5th Interview", "6th Interview",
                "Final Interview", "Offer Signed", "On Board", "Invoice", "Payment", "Successful");
        if (phaseList.contains(newPhase)) {
            // 通过候选人Id和职位Id，查询关联关系
            Optional<CandidateForCase> optional = candidateForCaseRepository.findByCandidateIdAndCaseId(candidateId, caseId).stream().findFirst();
            if (optional.isPresent()) {
                CandidateForCase candidateForCase = optional.get();
                // 其他阶段都直接更新
                candidateForCase.setFarthestPhase(newPhase);
                candidateForCaseRepository.save(candidateForCase);
            }
        }
    }
}
