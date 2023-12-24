package com.hello.background.service;

import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.constant.JobTypeEnum;
import com.hello.background.domain.CandidateForCase;
import com.hello.background.domain.CaseAttention;
import com.hello.background.domain.Client;
import com.hello.background.domain.ClientCase;
import com.hello.background.repository.*;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CaseQueryPageRequest;
import com.hello.background.vo.CaseVO;
import com.hello.background.vo.UserVO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Transactional
@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CaseAttentionRepository caseAttentionRepository;
    @Autowired
    private CandidateForCaseRepository candidateForCaseRepository;

    /**
     * 通过id查找职位
     *
     * @param id
     * @return
     */
    public CaseVO findById(Integer id) {
        Optional<ClientCase> clientCaseOptional = caseRepository.findById(id);
        if (!clientCaseOptional.isPresent()) {
            return null;
        }
        return TransferUtil.transferTo(clientCaseOptional.get(), CaseVO.class);
    }

    private CaseVO fromDoToVo(ClientCase clientCase) {
        CaseVO vo = TransferUtil.transferTo(clientCase, CaseVO.class);
        return vo;
    }

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    public CaseVO save(CaseVO vo) {
        ClientCase c = TransferUtil.transferTo(vo, ClientCase.class);
        Optional<Client> clientOptional = clientRepository.findById(c.getClientId());
        c.setClientChineseName(clientOptional.get().getChineseName());
        c = caseRepository.save(c);
        // 更新关联数据中的名称缓存
        if (null != vo.getId()) {
            // 更新关注职位
            List<CaseAttention> caseAttentionList = caseAttentionRepository.findByCaseId(vo.getId());
            caseAttentionList.forEach(ca -> {
                if (Strings.isNotBlank(ca.getCaseTitle()) && !ca.getCaseTitle().equals(vo.getTitle())) {
                    ca.setCaseTitle(vo.getTitle());
                    caseAttentionRepository.save(ca);
                }
            });
            // 更新候选人与职位的关联
            List<CandidateForCase> candidateForCaseList = candidateForCaseRepository.findByCaseId(vo.getId());
            candidateForCaseList.forEach(cc -> {
                if (Strings.isNotBlank(cc.getTitle()) && !cc.getTitle().equals(vo.getTitle())) {
                    cc.setTitle(vo.getTitle());
                    candidateForCaseRepository.save(cc);
                }
            });
        }
        return fromDoToVo(c);
    }

    /**
     * 查询分页
     *
     * @return
     */
    public Page<CaseVO> queryPage(CaseQueryPageRequest request, UserVO userVO) {
        Page<CaseVO> map = null;
        // 判断当前用户是否是体验用户
        boolean isExperience = userVO.getJobType().equals(JobTypeEnum.EXPERIENCE);
        if (isExperience) {
            List<CaseVO> all = caseRepository.findAll().stream().filter(c -> null != c.getShow4JobType() && c.getShow4JobType().contains(JobTypeEnum.EXPERIENCE)).map(x -> fromDoToVo(x)).collect(Collectors.toList());
            map = new PageImpl<>(all,
                    new PageRequest(0, all.size()),
                    all.size());
        } else {
            Pageable pageable = new PageRequest(request.getCurrentPage() - 1, request.getPageSize(), Sort.Direction.DESC, "id");
            Specification<ClientCase> specification = new Specification<ClientCase>() {
                @Override
                public Predicate toPredicate(Root<ClientCase> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    if (null != request.getSearch()) {
                        if (null != request.getSearch().getClientId()) {
                            list.add(criteriaBuilder.equal(root.get("clientId"), request.getSearch().getClientId()));
                        }
                        if (null != request.getSearch().getHrId()) {
                            list.add(criteriaBuilder.equal(root.get("hrId"), request.getSearch().getHrId()));
                        }
                        if (Strings.isNotBlank(request.getSearch().getTitle())) {
                            list.add(criteriaBuilder.like(root.get("title"), "%" + request.getSearch().getTitle() + "%"));
                        }
                        if (null != request.getSearch().getStatus()) {
                            list.add(criteriaBuilder.equal(root.get("status"), request.getSearch().getStatus()));
                        }
//                        if (!CollectionUtils.isEmpty(request.getSearch().getShow4JobType())) {
////                            list.add(root.<JobTypeEnum>get("show4JobType").in(request.getSearch().getShow4JobType()));
//                            list.add(criteriaBuilder.(root.get("show4JobType"), "EXPERIENCE"));
//                        }
                    }
                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }
            };
            Page<ClientCase> all = caseRepository.findAll(specification, pageable);
            map = all.map(x -> fromDoToVo(x));
            // 通过是否是体验用户，觉得返回内容和总元素数
            List<CaseVO> content = map.getContent();
            map = new PageImpl<>(content,
                    new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                    map.getTotalElements());
        }
        return map;
    }

    /**
     * 模糊查询职位信息
     *
     * @param search
     * @return
     */
    public List<CaseVO> query(String search) {
        List<ClientCase> caseList = caseRepository.findByTitleLikeOrDescriptionLikeOrderByIdDesc(search, search);
        return caseList.stream().map(x -> fromDoToVo(x)).collect(Collectors.toList());
    }

    /**
     * 通过职位名称和职位状态查询
     *
     * @param title
     * @param status
     * @return
     */
    public List<CaseVO> queryByTitleAndStatus(String title, CaseStatusEnum status) {
        List<ClientCase> caseList = caseRepository.findByTitleLikeAndStatusOrderByIdDesc(title, status);
        return caseList.stream().map(x -> fromDoToVo(x)).collect(Collectors.toList());
    }

    /**
     * 通过职位名称查找
     *
     * @param title
     * @return
     */
    public List<CaseVO> queryByTitle(String title) {
        List<ClientCase> caseList = caseRepository.findByTitleLikeOrderByIdDesc(title);
        return caseList.stream().map(x -> fromDoToVo(x)).collect(Collectors.toList());
    }

    /**
     * 通过id删除
     * findByCaseId
     *
     * @param id
     * @return
     */
    public String deleteById(Integer id) {
        Optional<ClientCase> optional = caseRepository.findById(id);
        if (optional.isPresent()) {
            caseRepository.deleteById(id);
            return "";
        } else {
            return "信息不存在！";
        }
    }

    /**
     * 清空体验岗位
     */
    public void clearExperience() {
        // 遍历所有职位，找到有体验选项的职位进行更新
        List<ClientCase> allCaseList = caseRepository.findAll();
        for (ClientCase cc : allCaseList) {
            if (null != cc.getShow4JobType() && cc.getShow4JobType().contains(JobTypeEnum.EXPERIENCE)) {
                cc.getShow4JobType().remove(JobTypeEnum.EXPERIENCE);
                caseRepository.save(cc);
            }
        }
    }
}
