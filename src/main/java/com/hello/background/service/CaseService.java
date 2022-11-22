package com.hello.background.service;

import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.domain.Client;
import com.hello.background.domain.ClientCase;
import com.hello.background.repository.CaseRepository;
import com.hello.background.repository.ClientRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CaseQueryPageRequest;
import com.hello.background.vo.CaseVO;
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
        return fromDoToVo(c);
    }

    /**
     * 查询分页
     *
     * @return
     */
    public Page<CaseVO> queryPage(CaseQueryPageRequest request) {
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
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<ClientCase> all = caseRepository.findAll(specification, pageable);
        Page<CaseVO> map = all.map(x -> fromDoToVo(x));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
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
}
