package com.hello.background.service;

import com.hello.background.domain.CandidateForCase;
import com.hello.background.domain.CaseAttention;
import com.hello.background.repository.CandidateForCaseRepository;
import com.hello.background.repository.CaseAttentionRepository;
import com.hello.background.repository.UserRepository;
import com.hello.background.vo.PipelineCandidateVO;
import com.hello.background.vo.PipelineCaseVO;
import com.hello.background.vo.PipelineVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 摘要服务器
 *
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Slf4j
@Transactional
@Service
public class SummaryService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CaseAttentionRepository caseAttentionRepository;
    @Autowired
    private CandidateForCaseRepository candidateForCaseRepository;
    @Autowired
    private UserService userService;

    /**
     * 查询pipeline情况
     *
     * @param range 查询范围
     * @return
     */
    public List<PipelineVO> queryPipeline(String range, UserVO userVO) {
        List<PipelineVO> pipelineVOList = new ArrayList<>();
        List<UserVO> userList = userService.findByScope(range, userVO);
        // 遍历用户列表，生成pipeline情况
        userList.stream().forEach(u -> pipelineVOList.add(generatePipeline(u)));
        return pipelineVOList;
    }

    /**
     * 生成pipeline情况
     *
     * @param userVO
     * @return
     */
    private PipelineVO generatePipeline(UserVO userVO) {
        PipelineVO pipelineVO = new PipelineVO();
        pipelineVO.setUser(userVO);
        // 用户关注的职位，按照ID倒排序（最新关注的在最上面）
        List<CaseAttention> caseAttentionList = caseAttentionRepository.findByUserNameOrderByClientChineseNameAscIdDesc(userVO.getUsername());
        List<PipelineCaseVO> pipelineCaseVOList = caseAttentionList.stream().map(ca -> {
            PipelineCaseVO pc = new PipelineCaseVO();
            pc.setClientId(ca.getClientId());
            pc.setClientChineseName(ca.getClientChineseName());
            pc.setId(ca.getCaseId());
            pc.setTitle(ca.getCaseTitle());
            return pc;
        }).collect(Collectors.toList());
        pipelineVO.setPipelineCaseList(pipelineCaseVOList);
        // 遍历pipelinecase，填充候选人情况
        pipelineVO.getPipelineCaseList().forEach(c -> generateCandidateList(c));
        return pipelineVO;
    }

    /**
     * 生成候选人信息
     *
     * @param pipelineCaseVO
     */
    private void generateCandidateList(PipelineCaseVO pipelineCaseVO) {
        // 获取该职位下所有的候选人
        List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCaseId(pipelineCaseVO.getId());
        // 根据候选人与职位关联信息中的最后阶段分类
        candidateForCaseList.stream().forEach(c -> {
            if (null != c.getLastPhase()) {
                switch (c.getLastPhase()) {
                    case "Payment":
                        pipelineCaseVO.getPaymentCandidateList().add(new PipelineCandidateVO(c.getCandidateId(), c.getChineseName()));
                        break;
                    case "Invoice":
                        pipelineCaseVO.getInvoiceCandidateList().add(new PipelineCandidateVO(c.getCandidateId(), c.getChineseName()));
                        break;
                    case "On Board":
                        pipelineCaseVO.getOnboardCandidateList().add(new PipelineCandidateVO(c.getCandidateId(), c.getChineseName()));
                        break;
                    case "Offer Signed":
                        pipelineCaseVO.getOfferCandidateList().add(new PipelineCandidateVO(c.getCandidateId(), c.getChineseName()));
                        break;
                    case "Final Interview":
                    case "4th Interview":
                    case "3rd Interview":
                    case "2nd Interview":
                    case "1st Interview":
                        pipelineCaseVO.getInterviewCandidateList().add(new PipelineCandidateVO(c.getCandidateId(), c.getChineseName() + "-" + c.getLastPhase().substring(0, 1)));
                        break;
                    case "CVO":
                        pipelineCaseVO.getCvoCandidateList().add(new PipelineCandidateVO(c.getCandidateId(), c.getChineseName()));
                        break;
                    case "IOI":
                    case "VI":
                        pipelineCaseVO.getViioiCandidateList().add(new PipelineCandidateVO(c.getCandidateId(), c.getChineseName()));
                        break;
                    case "END":
                    default:
                        break;
                }
            }
        });
    }
}
