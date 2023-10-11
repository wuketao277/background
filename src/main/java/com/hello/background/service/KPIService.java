package com.hello.background.service;

import com.hello.background.domain.KPI;
import com.hello.background.repository.KPIRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.KPIVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuketao
 * @date 2023/10/10
 * @Description
 */
@Transactional
@Slf4j
@Service
public class KPIService {
    @Autowired
    private KPIRepository kpiRepository;

    /**
     * 通过月份删除
     *
     * @param month
     */
    public void deleteByMonth(String month) {
        kpiRepository.deleteByMonth(month);
    }

    /**
     * 保存KPI数据
     *
     * @param kpi
     */
    public void save(KPI kpi) {
        kpiRepository.save(kpi);
    }

    /**
     * 通过月份查询
     *
     * @param month
     * @return
     */
    public List<KPI> findAllByMonth(String month) {
        return kpiRepository.findAllByMonth(month);
    }

    /**
     * 通过月份和用户名查询
     *
     * @param month
     * @param userName
     * @return
     */
    public KPI findByMonthAndUserName(String month, String userName) {
        return kpiRepository.findByMonthAndUserName(month, userName);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<KPIVO> queryKPIVOPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Specification<KPI> specification = new Specification<KPI>() {
            @Override
            public Predicate toPredicate(Root<KPI> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (Strings.isNotBlank(search)) {
                    list.add(criteriaBuilder.or(criteriaBuilder.like(root.get("realName"), "%" + search + "%"), criteriaBuilder.like(root.get("userName"), "%" + search + "%"), criteriaBuilder.like(root.get("month"), "%" + search + "%")));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<KPI> all = kpiRepository.findAll(specification, pageable);
        Page<KPIVO> map = all.map(x -> TransferUtil.transferTo(x, KPIVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }
}
