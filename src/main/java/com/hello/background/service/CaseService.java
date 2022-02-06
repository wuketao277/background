package com.hello.background.service;

import com.hello.background.common.CommonUtils;
import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.domain.Client;
import com.hello.background.domain.ClientCase;
import com.hello.background.repository.CaseRepository;
import com.hello.background.repository.ClientRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
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
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<CaseVO> queryPage(String search, String searchStatus, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        List<String> searchWordList = CommonUtils.splitSearchWord(search);
        Specification<ClientCase> specification = new Specification<ClientCase>() {
            @Override
            public Predicate toPredicate(Root<ClientCase> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                for (String searchWord : searchWordList) {
                    Path<String> titlePath = root.get("title");
                    Predicate titleLike = criteriaBuilder.like(titlePath, "%" + searchWord + "%");
                    Path<String> descriptionPath = root.get("description");
                    Predicate descriptionLike = criteriaBuilder.like(descriptionPath, "%" + searchWord + "%");
                    Path<String> clientChineseNamePath = root.get("clientChineseName");
                    Predicate clientChineseNameLike = criteriaBuilder.like(clientChineseNamePath, "%" + searchWord + "%");
                    list.add(criteriaBuilder.and(criteriaBuilder.or(titleLike, descriptionLike, clientChineseNameLike)));
                }
                if (!StringUtils.isEmpty(searchStatus) && !"ALL".equals(searchStatus)) {
                    Path<String> path = root.get("status");
                    CaseStatusEnum statusEnum = CaseStatusEnum.LOOP.get(searchStatus);
                    Predicate like = criteriaBuilder.equal(path, statusEnum);
                    list.add(criteriaBuilder.and(like));
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
}
