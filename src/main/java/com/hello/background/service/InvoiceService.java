package com.hello.background.service;

import com.hello.background.domain.Invoice;
import com.hello.background.repository.InvoiceRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.InvoiceVO;
import com.hello.background.vo.InvoiceVOPageRequest;
import com.hello.background.vo.InvoiceVOPageSearchRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author wuketao
 * @date 2022/9/25
 * @Description
 */
@Transactional
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    public InvoiceVO save(InvoiceVO vo) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(vo, invoice);
        invoice = invoiceRepository.save(invoice);
        vo.setId(invoice.getId());
        return vo;
    }

    /**
     * 查询分页
     *
     * @return
     */
    public Page<InvoiceVO> queryPage(InvoiceVOPageRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(request.getCurrentPage() - 1, request.getPageSize(), sort);
        Specification<Invoice> specification = new Specification<Invoice>() {
            @Override
            public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                InvoiceVOPageSearchRequest search = request.getSearch();
                if (null != search.getClientId()) {
                    Path<String> path = root.get("clientId");
                    Predicate like = criteriaBuilder.equal(path, search.getClientId());
                    list.add(criteriaBuilder.and(like));
                }
                if (null != search.getAmId()) {
                    Path<Integer> path = root.get("amId");
                    Predicate equal = criteriaBuilder.equal(path, search.getAmId());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getCandidateChineseName()) {
                    Path<String> path = root.get("candidateChineseName");
                    Predicate equal = criteriaBuilder.like(path, "%" + search.getCandidateChineseName() + "%");
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getType()) {
                    Path<String> path = root.get("type");
                    Predicate equal = criteriaBuilder.equal(path, search.getType());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getStatus()) {
                    Path<String> path = root.get("status");
                    Predicate equal = criteriaBuilder.equal(path, search.getStatus());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getCreateDateStart()) {
                    Path<Date> startPath = root.get("createDate");
                    Predicate startEqual = criteriaBuilder.greaterThanOrEqualTo(startPath, search.getCreateDateStart());
                    list.add(criteriaBuilder.and(startEqual));
                }
                if (null != search.getCreateDateEnd()) {
                    Path<Date> endPath = root.get("createDate");
                    Predicate endEqual = criteriaBuilder.lessThanOrEqualTo(endPath, search.getCreateDateEnd());
                    list.add(criteriaBuilder.and(endEqual));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<Invoice> all = invoiceRepository.findAll(specification, pageable);
        Page<InvoiceVO> map = all.map(x -> TransferUtil.transferTo(x, InvoiceVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    public String deleteById(Integer id) {
        Optional<Invoice> optional = invoiceRepository.findById(id);
        if (optional.isPresent()) {
            invoiceRepository.deleteById(id);
            return "";
        } else {
            return "信息不存在！";
        }
    }

    /**
     * 获取未付款金额
     *
     * @return
     */
    public BigDecimal getNoPaymentSum() {
        return invoiceRepository.findByActualPaymentDateIsNull().stream().map(Invoice::getSum).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
