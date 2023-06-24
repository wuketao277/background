package com.hello.background.service;

import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.KPIWorkDaysAdjust;
import com.hello.background.repository.KPIWorkDaysAdjustRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.KPIWorkDaysAdjustVO;
import com.hello.background.vo.UserVO;
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

/**
 * @author wuketao
 * @date 2023/6/22
 * @Description
 */
@Transactional
@Service
public class KPIWorkDaysAdjustService {
    @Autowired
    private KPIWorkDaysAdjustRepository repository;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    public KPIWorkDaysAdjustVO save(KPIWorkDaysAdjustVO vo) {
        KPIWorkDaysAdjust kpiWorkDaysAdjust = TransferUtil.transferTo(vo, KPIWorkDaysAdjust.class);
        kpiWorkDaysAdjust = repository.save(kpiWorkDaysAdjust);
        return TransferUtil.transferTo(kpiWorkDaysAdjust, KPIWorkDaysAdjustVO.class);
    }

    /**
     * 通过ID删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    /**
     * 分页查询
     *
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<KPIWorkDaysAdjustVO> queryPage(String search, Integer currentPage, Integer pageSize, UserVO user) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, sort);
        // 设置查询条件
        Specification<KPIWorkDaysAdjust> specification = new Specification<KPIWorkDaysAdjust>() {
            @Override
            public Predicate toPredicate(Root<KPIWorkDaysAdjust> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!user.getRoles().contains(RoleEnum.ADMIN) && !user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    // 普通用户只能查询自己的信息
                    list.add(criteriaBuilder.equal(root.get("userName"), user.getUsername()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<KPIWorkDaysAdjust> all = repository.findAll(specification, pageable);
        Page<KPIWorkDaysAdjustVO> map = all.map(x -> TransferUtil.transferTo(x, KPIWorkDaysAdjustVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public KPIWorkDaysAdjustVO queryById(Integer id) {
        Optional<KPIWorkDaysAdjust> optional = repository.findById(id);
        if (optional.isPresent()) {
            return TransferUtil.transferTo(optional.get(), KPIWorkDaysAdjustVO.class);
        } else {
            return null;
        }
    }
}
