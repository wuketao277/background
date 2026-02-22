package com.hello.background.service;

import com.hello.background.domain.CostInvoice;
import com.hello.background.repository.CostInvoiceRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CostInvoiceVO;
import com.hello.background.vo.CostInvoiceVOPageRequest;
import com.hello.background.vo.CostInvoiceVOPageSearchRequest;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
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
public class CostInvoiceService {

    @Autowired
    private CostInvoiceRepository costInvoiceRepository;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    public CostInvoiceVO save(CostInvoiceVO vo, UserVO userVO) {
        if (null == vo.getId()) {
            // 如果没有id，表示为新增。系统自动添加当前用户为创建人
            vo.setConsultantId(userVO.getId());
            vo.setConsultantUserName(userVO.getUsername());
            vo.setConsultantRealName(userVO.getRealname());
        }
        CostInvoice costInvoice = TransferUtil.transferTo(vo, CostInvoice.class);
        costInvoice = costInvoiceRepository.save(costInvoice);
        return TransferUtil.transferTo(costInvoice, CostInvoiceVO.class);
    }

    /**
     * 查询分页
     *
     * @return
     */
    public Page<CostInvoiceVO> queryPage(CostInvoiceVOPageRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "submitDate");
        Pageable pageable = new PageRequest(request.getCurrentPage() - 1, request.getPageSize(), sort);
        Specification<CostInvoice> specification = new Specification<CostInvoice>() {
            @Override
            public Predicate toPredicate(Root<CostInvoice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                CostInvoiceVOPageSearchRequest search = request.getSearch();
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
        Page<CostInvoice> all = costInvoiceRepository.findAll(specification, pageable);
        Page<CostInvoiceVO> map = all.map(x -> TransferUtil.transferTo(x, CostInvoiceVO.class));
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
        costInvoiceRepository.deleteById(id);
    }
}
