package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.SalarySpecialItem;
import com.hello.background.repository.SalarySpecialItemRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.SalarySpecialItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工资特殊项服务
 *
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@Transactional
@Service
public class SalarySpecialItemService {

    @Autowired
    private SalarySpecialItemRepository salarySpecialItemRepository;

    public SalarySpecialItemVO save(SalarySpecialItemVO vo) {
        SalarySpecialItem salarySpecialItem = new SalarySpecialItem();
        BeanUtils.copyProperties(vo, salarySpecialItem);
        salarySpecialItem = salarySpecialItemRepository.save(salarySpecialItem);
        vo.setId(salarySpecialItem.getId());
        return vo;
    }


    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<SalarySpecialItemVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Page<SalarySpecialItem> salarySpecialItemPage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            salarySpecialItemPage = salarySpecialItemRepository.findAll(pageable);
            total = salarySpecialItemRepository.count();
        } else {
            salarySpecialItemPage = salarySpecialItemRepository.findByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrDescriptionLikeOrderByUpdateTimeDesc(search, search, search, search, pageable);
            total = salarySpecialItemRepository.countByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrDescriptionLikeOrderByUpdateTimeDesc(search, search, search, search);
        }
        Page<SalarySpecialItemVO> map = salarySpecialItemPage.map(x -> TransferUtil.transferTo(x, SalarySpecialItemVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }
}
