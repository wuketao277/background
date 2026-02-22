package com.hello.background.service;

import com.hello.background.constant.ApproveStatusEnum;
import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.CostInvoice;
import com.hello.background.domain.CostInvoiceUsed;
import com.hello.background.repository.CostInvoiceRepository;
import com.hello.background.repository.CostInvoiceUsedRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private CostInvoiceUsedRepository costInvoiceUsedRepository;

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
    public Page<CostInvoiceVO> queryPage(CostInvoiceVOPageRequest request, UserVO userVO) {
        Sort sort = new Sort(Sort.Direction.DESC, "submitDate");
        Pageable pageable = new PageRequest(request.getCurrentPage() - 1, request.getPageSize(), sort);
        Specification<CostInvoice> specification = new Specification<CostInvoice>() {
            @Override
            public Predicate toPredicate(Root<CostInvoice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                CostInvoiceVOPageSearchRequest search = request.getSearch();
                if (Strings.isNotBlank(search.getConsultantUserName())) {
                    Path<Integer> path = root.get("consultantUserName");
                    Predicate equal = criteriaBuilder.equal(path, search.getConsultantUserName());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getInvoiceDate()) {
                    Path<String> path = root.get("invoiceDate");
                    Predicate equal = criteriaBuilder.equal(path, search.getInvoiceDate());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getSubmitDate()) {
                    Path<String> path = root.get("submitDate");
                    Predicate equal = criteriaBuilder.equal(path, search.getSubmitDate());
                    list.add(criteriaBuilder.and(equal));
                }
                if (Strings.isNotBlank(search.getInvoiceNumber())) {
                    Path<String> path = root.get("invoiceNumber");
                    Predicate equal = criteriaBuilder.like(path, "%" + search.getInvoiceNumber() + "%");
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getSum()) {
                    Path<String> path = root.get("sum");
                    Predicate equal = criteriaBuilder.equal(path, search.getSum());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getInvoiceType()) {
                    Path<String> path = root.get("invoiceType");
                    Predicate equal = criteriaBuilder.equal(path, search.getInvoiceType());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getKind()) {
                    Path<String> path = root.get("kind");
                    Predicate equal = criteriaBuilder.equal(path, search.getKind());
                    list.add(criteriaBuilder.and(equal));
                }
                if (Strings.isNotBlank(search.getRemark())) {
                    Path<String> path = root.get("remark");
                    Predicate equal = criteriaBuilder.like(path, "%" + search.getRemark() + "%");
                    list.add(criteriaBuilder.and(equal));
                }
                if (!userVO.getRoles().contains(RoleEnum.ADMIN)) {
                    // 如果不是管理员，只能查看自己的成本发票数据
                    Path<String> path = root.get("consultantId");
                    Predicate equal = criteriaBuilder.equal(path, userVO.getId());
                    list.add(criteriaBuilder.and(equal));
                }
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

    /**
     * 计算可用金额
     *
     * @param userVO
     * @return
     */
    public BigDecimal calcAvailableAmount(UserVO userVO) {
        // 查询当前用户所有审批过的成本发票
        List<CostInvoice> costInvoiceList = costInvoiceRepository.findAllByConsultantIdAndApproveStatus(userVO.getId(), ApproveStatusEnum.APPROVED.getCode());
        // 累加计算成本发票的价税合计
        BigDecimal sum = costInvoiceList.stream().map(CostInvoice::getSum).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 减去已经使用的金额
        List<CostInvoiceUsed> costInvoiceUsedList = costInvoiceUsedRepository.findAllByConsultantId(userVO.getId());
        sum = sum.subtract(costInvoiceUsedList.stream().map(CostInvoiceUsed::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));
        return sum;
    }

    /**
     * 计算所有顾问可用金额
     *
     * @return
     */
    public List<CostInvoiceAvailableAmountVO> calcAllConsultantAvailableAmount() {
        List<CostInvoiceAvailableAmountVO> availableAmountVOList = new ArrayList<>();
        // 查询所有审批通过的成本发票，按照顾问id进行分组计算总和
        List<CostInvoice> costInvoiceList = costInvoiceRepository.findAllByApproveStatus(ApproveStatusEnum.APPROVED.getCode());
        costInvoiceList.forEach(x -> {
            Optional<CostInvoiceAvailableAmountVO> first = availableAmountVOList.stream().filter(y -> y.getConsultantId().equals(x.getConsultantId())).findFirst();
            if (first.isPresent()) {
                // 如果已存在就累加
                first.get().setSum(first.get().getSum().add(x.getSum()));
            } else {
                // 如果不存在就创建
                CostInvoiceAvailableAmountVO vo = new CostInvoiceAvailableAmountVO(x.getConsultantId(), x.getConsultantUserName(), x.getConsultantRealName(), x.getSum());
                availableAmountVOList.add(vo);
            }
        });
        // 减去已经使用的金额
        List<CostInvoiceUsed> costInvoiceUsedList = (List<CostInvoiceUsed>) costInvoiceUsedRepository.findAll();
        costInvoiceUsedList.forEach(x -> {
            Optional<CostInvoiceAvailableAmountVO> first = availableAmountVOList.stream().filter(y -> y.getConsultantId().equals(x.getConsultantId())).findFirst();
            if (first.isPresent()) {
                first.get().setSum(first.get().getSum().subtract(x.getSum()));
            } else {
                CostInvoiceAvailableAmountVO vo = new CostInvoiceAvailableAmountVO(x.getConsultantId(), x.getConsultantUserName(), x.getConsultantRealName(), x.getSum().negate());
                availableAmountVOList.add(vo);
            }
        });
        return availableAmountVOList;
    }
}
