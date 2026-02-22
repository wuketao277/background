package com.hello.background.service;

import com.hello.background.domain.CostInvoiceUsed;
import com.hello.background.repository.CostInvoiceUsedRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CostInvoiceUsedVO;
import com.hello.background.vo.CostInvoiceUsedVOPageRequest;
import com.hello.background.vo.CostInvoiceUsedVOPageSearchRequest;
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

/**
 * 成本发票服务
 *
 * @author wuketao
 * @date 2026/2/22
 * @Description
 */
@Transactional
@Service
public class CostInvoiceUsedService {

    @Autowired
    private CostInvoiceUsedRepository costInvoiceUsedRepository;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    public CostInvoiceUsedVO save(CostInvoiceUsedVO vo) {
        CostInvoiceUsed costInvoiceUsed = TransferUtil.transferTo(vo, CostInvoiceUsed.class);
        costInvoiceUsed = costInvoiceUsedRepository.save(costInvoiceUsed);
        return TransferUtil.transferTo(costInvoiceUsed, CostInvoiceUsedVO.class);
    }

    /**
     * 查询分页
     *
     * @return
     */
    public Page<CostInvoiceUsedVO> queryPage(CostInvoiceUsedVOPageRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "usedDate");
        Pageable pageable = new PageRequest(request.getCurrentPage() - 1, request.getPageSize(), sort);
        Specification<CostInvoiceUsed> specification = new Specification<CostInvoiceUsed>() {
            @Override
            public Predicate toPredicate(Root<CostInvoiceUsed> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                CostInvoiceUsedVOPageSearchRequest search = request.getSearch();
//                if (null != search.getAmId()) {
//                    Path<Integer> path = root.get("amId");
//                    Predicate equal = criteriaBuilder.equal(path, search.getAmId());
//                    list.add(criteriaBuilder.and(equal));
//                }
//                if (null != search.getType()) {
//                    Path<String> path = root.get("type");
//                    Predicate equal = criteriaBuilder.equal(path, search.getType());
//                    list.add(criteriaBuilder.and(equal));
//                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<CostInvoiceUsed> all = costInvoiceUsedRepository.findAll(specification, pageable);
        Page<CostInvoiceUsedVO> map = all.map(x -> TransferUtil.transferTo(x, CostInvoiceUsedVO.class));
        map = new PageImpl<>(map.getContent(), new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()), all.getTotalElements());
        return map;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    public void deleteById(Integer id) {
        costInvoiceUsedRepository.deleteById(id);
    }
}
