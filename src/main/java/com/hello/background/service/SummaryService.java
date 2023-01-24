package com.hello.background.service;

import com.hello.background.constant.CompanyEnum;
import com.hello.background.constant.JobTypeEnum;
import com.hello.background.domain.CandidateForCase;
import com.hello.background.domain.CaseAttention;
import com.hello.background.domain.User;
import com.hello.background.repository.CandidateForCaseRepository;
import com.hello.background.repository.CaseAttentionRepository;
import com.hello.background.repository.UserRepository;
import com.hello.background.utils.TransferUtil;
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

    /**
     * 查询pipeline情况
     *
     * @param range 查询范围
     * @return
     */
    public List<PipelineVO> queryPipeline(String range, UserVO userVO) {
        List<PipelineVO> pipelineVOList = new ArrayList<>();
        List<UserVO> userList = new ArrayList<>();
        if ("self".equals(range)) {
            // 只查看自己的pipeline情况
            userList.add(userVO);
        } else if ("all".equals(range)) {
            // 查看所有在职的全职人员pipeline情况
            userList = userRepository.findAll().stream().filter(u -> u.getEnabled() && null != u.getJobType() && u.getJobType().equals(JobTypeEnum.FULLTIME) && null == u.getDimissionDate()).map(u -> TransferUtil.transferTo(u, UserVO.class)).collect(Collectors.toList());
        } else if ("shanghai".equals(range)) {
            // 查看上海所有在职的全职人员pipeline情况
            userList = userRepository.findAll().stream().filter(u -> u.getEnabled() && null != u.getJobType() && u.getJobType().equals(JobTypeEnum.FULLTIME) && null == u.getDimissionDate() && null != u.getCompany() && u.getCompany().equals(CompanyEnum.Shanghaihailuorencaifuwu)).map(u -> TransferUtil.transferTo(u, UserVO.class)).collect(Collectors.toList());
        } else if ("shenyang".equals(range)) {
            // 查看沈阳所有在职的全职人员pipeline情况
            userList = userRepository.findAll().stream().filter(u -> u.getEnabled() && null != u.getJobType() && u.getJobType().equals(JobTypeEnum.FULLTIME) && null == u.getDimissionDate() && null != u.getCompany() && u.getCompany().equals(CompanyEnum.Shenyanghailuorencaifuwu)).map(u -> TransferUtil.transferTo(u, UserVO.class)).collect(Collectors.toList());
        } else if ("beijing".equals(range)) {
            // 查看北京所有在职的全职人员pipeline情况
            List<User> list = new ArrayList<>();
            list.add(userRepository.findByUsername("Victor"));
            list.add(userRepository.findByUsername("Ellen"));
            userList = list.stream().map(u -> TransferUtil.transferTo(u, UserVO.class)).collect(Collectors.toList());
        }
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
                        pipelineCaseVO.getInterviewCandidateList().add(new PipelineCandidateVO(c.getCandidateId(), c.getChineseName()));
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
