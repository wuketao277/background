package com.hello.background.service;

import com.hello.background.domain.KPIWorkDaysAdjust;
import com.hello.background.repository.KPIWorkDaysAdjustRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.KPIWorkDaysAdjustVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<KPIWorkDaysAdjustVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, sort);
        Page<KPIWorkDaysAdjust> all = repository.findAll(pageable);
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
