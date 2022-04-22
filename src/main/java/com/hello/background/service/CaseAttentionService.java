package com.hello.background.service;

import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.domain.*;
import com.hello.background.repository.CandidateForCaseRepository;
import com.hello.background.repository.CaseAttentionRepository;
import com.hello.background.repository.CaseRepository;
import com.hello.background.repository.CommentRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 职位关注服务
 *
 * @author wuketao
 * @date 2022/3/6
 * @Description
 */
@Transactional
@Slf4j
@Service
public class CaseAttentionService {
    @Autowired
    private CaseAttentionRepository caseAttentionRepository;
    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private CandidateForCaseRepository candidateForCaseRepository;
    @Autowired
    private CommentRepository commentRepository;

    /**
     * 查询关注列表
     *
     * @param userName
     */
    public List<CaseAttentionVO> queryAttentionListByUserName(String userName) {
        List<CaseAttention> caseAttentionList = caseAttentionRepository.findByUserName(userName);
        return caseAttentionList.stream().map(c -> TransferUtil.transferTo(c, CaseAttentionVO.class)).collect(Collectors.toList());
    }

    /**
     * 查询某个职位的关注
     *
     * @param userName
     */
    public boolean queryAttentionByCaseIdAndUserName(Integer caseId, String userName) {
        List<CaseAttention> list = caseAttentionRepository.findByCaseIdAndUserName(caseId, userName);
        if (CollectionUtils.isNotEmpty(list)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询所有关注职位信息
     *
     * @param userVO
     * @return
     */
    public List<CaseAttention4ClientVO> queryAllCaseAttention(UserVO userVO) {
        List<CaseAttention> caseAttentionList = caseAttentionRepository.findByUserName(userVO.getUsername());
        if (CollectionUtils.isEmpty(caseAttentionList)) {
            return Collections.EMPTY_LIST;
        } else {
            List<CaseAttention4ClientVO> clientVOList = new ArrayList<>();
            // 所有关注的职位，按照职位id排序。
            caseAttentionList = caseAttentionList.stream().sorted(Comparator.comparing(CaseAttention::getCaseId)).collect(Collectors.toList());
            // 遍历所有关注的职位
            for (CaseAttention caseAttention : caseAttentionList) {
                // 首先获取职位所属客户
                CaseAttention4ClientVO clientVO = new CaseAttention4ClientVO();
                if (clientVOList.stream().anyMatch(c -> c.getClientId().equals(caseAttention.getClientId()))) {
                    clientVO = clientVOList.stream().filter(c -> c.getClientId().equals(caseAttention.getClientId())).findFirst().get();
                } else {
                    clientVO.setClientId(caseAttention.getClientId());
                    clientVO.setClientChineseName(caseAttention.getClientChineseName());
                    clientVOList.add(clientVO);
                }
                // 获取职位信息
                List<CaseAttention4CaseVO> caseVOList = clientVO.getCaseList();
                CaseAttention4CaseVO caseVO = new CaseAttention4CaseVO();
                if (caseVOList.stream().anyMatch(c -> c.getCaseId().equals(caseAttention.getCaseId()))) {
                    caseVO = caseVOList.stream().filter(c -> c.getCaseId().equals(caseAttention.getCaseId())).findFirst().get();
                } else {
                    caseVO.setCaseId(caseAttention.getCaseId());
                    caseVO.setCaseTitle(caseAttention.getCaseTitle());
                    clientVO.getCaseList().add(caseVO);
                }
                // 获取职位下的关注的候选人信息
                List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCaseIdAndAttention(caseVO.getCaseId(), true);
                for (CandidateForCase candidateForCase : candidateForCaseList) {
                    CaseAttention4CandidateVO candidateVO = new CaseAttention4CandidateVO();
                    candidateVO.setCandidateId(candidateForCase.getCandidateId());
                    candidateVO.setCandidateChineseName(candidateForCase.getChineseName());
                    // 获取候选人最后评论信息
                    List<Comment> commentList = commentRepository.findAllByCandidateId(candidateVO.getCandidateId()).stream().sorted(Comparator.comparing(Comment::getId).reversed()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(commentList)) {
                        Comment comment = commentList.get(0);
                        candidateVO.setLatestCommentContent(comment.getContent());
                        candidateVO.setLatestCommentInputtime(comment.getInputTime());
                        candidateVO.setLatestCommentUsername(comment.getUsername());
                    }
                    caseVO.getCandidateList().add(candidateVO);
                }
            }
            return clientVOList.stream().sorted(Comparator.comparing(CaseAttention4ClientVO::getClientId)).collect(Collectors.toList());
        }
    }


    /**
     * 查询当前用户所有对接的职位
     *
     * @param userVO
     * @return
     */
    public List<CaseAttention4ClientVO> queryAllCWCase(UserVO userVO) {
        List<ClientCase> caseList = caseRepository.findByCwUserNameAndStatus(userVO.getUsername(), CaseStatusEnum.DOING);
        if (CollectionUtils.isEmpty(caseList)) {
            return Collections.EMPTY_LIST;
        } else {
            List<CaseAttention4ClientVO> clientVOList = new ArrayList<>();
            caseList = caseList.stream().sorted(Comparator.comparing(ClientCase::getId)).collect(Collectors.toList());
            // 遍历所有对接的职位
            for (ClientCase clientCase : caseList) {
                // 首先获取职位所属客户
                CaseAttention4ClientVO clientVO = new CaseAttention4ClientVO();
                if (clientVOList.stream().anyMatch(c -> c.getClientId().equals(clientCase.getClientId()))) {
                    clientVO = clientVOList.stream().filter(c -> c.getClientId().equals(clientCase.getClientId())).findFirst().get();
                } else {
                    clientVO.setClientId(clientCase.getClientId());
                    clientVO.setClientChineseName(clientCase.getClientChineseName());
                    clientVOList.add(clientVO);
                }
                // 获取职位信息
                CaseAttention4CaseVO caseVO = new CaseAttention4CaseVO();
                caseVO.setCaseId(clientCase.getId());
                caseVO.setCaseTitle(clientCase.getTitle());
                clientVO.getCaseList().add(caseVO);
                // 获取职位下的关注的候选人信息
                List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCaseIdAndAttention(caseVO.getCaseId(), true);
                for (CandidateForCase candidateForCase : candidateForCaseList) {
                    CaseAttention4CandidateVO candidateVO = new CaseAttention4CandidateVO();
                    candidateVO.setCandidateId(candidateForCase.getCandidateId());
                    candidateVO.setCandidateChineseName(candidateForCase.getChineseName());
                    // 获取候选人最后评论信息
                    List<Comment> commentList = commentRepository.findAllByCandidateId(candidateVO.getCandidateId()).stream().sorted(Comparator.comparing(Comment::getId).reversed()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(commentList)) {
                        Comment comment = commentList.get(0);
                        candidateVO.setLatestCommentContent(comment.getContent());
                        candidateVO.setLatestCommentInputtime(comment.getInputTime());
                        candidateVO.setLatestCommentUsername(comment.getUsername());
                    }
                    caseVO.getCandidateList().add(candidateVO);
                }
            }
            return clientVOList.stream().sorted(Comparator.comparing(CaseAttention4ClientVO::getClientId)).collect(Collectors.toList());
        }
    }

    /**
     * 添加关注
     *
     * @param caseId
     */
    public void addAttention(Integer caseId, UserVO user) {
        List<CaseAttention> list = caseAttentionRepository.findByCaseIdAndUserName(caseId, user.getUsername());
        if (CollectionUtils.isEmpty(list)) {
            // 后增加
            Optional<ClientCase> clientCaseOptional = caseRepository.findById(caseId);
            if (clientCaseOptional.isPresent()) {
                ClientCase clientCase = clientCaseOptional.get();
                CaseAttention ca = new CaseAttention();
                ca.setClientId(clientCase.getClientId());
                ca.setClientChineseName(clientCase.getClientChineseName());
                ca.setCaseId(caseId);
                ca.setCaseTitle(clientCase.getTitle());
                ca.setUserName(user.getUsername());
                caseAttentionRepository.save(ca);
            }
        }
    }

    /**
     * 移除关注
     *
     * @param caseId
     * @param userName
     */
    public void removeAttention(Integer caseId, String userName) {
        caseAttentionRepository.deleteByCaseIdAndUserName(caseId, userName);
    }
}
