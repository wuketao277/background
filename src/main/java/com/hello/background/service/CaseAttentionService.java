package com.hello.background.service;

import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.domain.CandidateForCase;
import com.hello.background.domain.CaseAttention;
import com.hello.background.domain.ClientCase;
import com.hello.background.domain.Comment;
import com.hello.background.repository.*;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private UserRepository userRepository;

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
     * 处理关注职位信息
     *
     * @return
     */
    private List<CaseAttention4ClientVO> dealwithCaseAttention(List<CaseAttention> caseAttentionList) {
        if (CollectionUtils.isEmpty(caseAttentionList)) {
            return Collections.EMPTY_LIST;
        } else {
            // 客户列表
            List<CaseAttention4ClientVO> clientVOList = new ArrayList<>();
            // 遍历所有关注的职位
            for (CaseAttention caseAttention : caseAttentionList) {
                // 首先获取职位所属客户
                CaseAttention4ClientVO clientVO = new CaseAttention4ClientVO();
                if (clientVOList.stream().anyMatch(c -> c.getClientId().equals(caseAttention.getClientId()))) {
                    // 如果客户在列表中已存在，就从列表中获取。
                    clientVO = clientVOList.stream().filter(c -> c.getClientId().equals(caseAttention.getClientId())).findFirst().get();
                } else {
                    // 如果客户不在列表中，就创建一个新对象，存入列表中。
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
                List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCaseIdAndAttentionOrderByCandidateIdDesc(caseVO.getCaseId(), true);
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
            return clientVOList;
        }
    }

    /**
     * 查询所有关注职位信息
     *
     * @param userVO
     * @return
     */
    public List<CaseAttention4ClientVO> queryAllCaseAttention(UserVO userVO) {
        // 用户关注的职位，按照ID倒排序（最新关注的在最上面）
        List<CaseAttention> caseAttentionList = caseAttentionRepository.findByUserNameOrderByIdDesc(userVO.getUsername());
        return dealwithCaseAttention(caseAttentionList);
    }

    /**
     * 查询所有关注职位信息
     *
     * @param userVO
     * @return
     */
    public List<CaseAttentionVO> queryAllCaseAttention2(UserVO userVO) {
        // 用户关注的职位，按照ID倒排序（最新关注的在最上面）
        List<CaseAttention> caseAttentionList = caseAttentionRepository.findByUserNameOrderByIdDesc(userVO.getUsername());
        return caseAttentionList.stream().map(ca -> TransferUtil.transferTo(ca, CaseAttentionVO.class)).collect(Collectors.toList());
    }

    /**
     * 查询所有关注职位信息
     *
     * @return
     */
    public List<CaseAttention4ClientVO> queryAllUserCaseAttention() {
        List<Sort.Order> orderList = new ArrayList<>();
        orderList.add(new Sort.Order(Sort.Direction.ASC, "userName"));
        orderList.add(new Sort.Order(Sort.Direction.ASC, "clientChineseName"));
        orderList.add(new Sort.Order(Sort.Direction.ASC, "id"));
        // 在职用户关注的职位，按照用户和ID倒排序（最新关注的在最上面）
        List<String> userNameList = userRepository.findByEnabled(true).stream().map(user -> user.getUsername()).collect(Collectors.toList());
        List<CaseAttention> caseAttentionList = caseAttentionRepository.findAll(new Sort(orderList)).parallelStream().filter(c -> userNameList.contains(c.getUserName())).collect(Collectors.toList());
        return dealwithCaseAttention(caseAttentionList);
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
