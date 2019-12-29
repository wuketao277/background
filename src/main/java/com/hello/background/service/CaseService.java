package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.Case;
import com.hello.background.repository.CaseRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.CaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    public CaseVO save(CaseVO vo) {
        Case c = (Case) TransferUtil.transferTo(vo, Case.class);
        c = caseRepository.save(c);
        return (CaseVO) TransferUtil.transferTo(c, Case.class);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<CaseVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Case> casePage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            casePage = caseRepository.findAll(pageable);
            total = caseRepository.count();
        } else {
            casePage = caseRepository.findByTitleLike(search, pageable);
            total = caseRepository.countByTitleLike(search);
        }
        Page<CaseVO> map = casePage.map(x -> (CaseVO) TransferUtil.transferTo(x, CaseVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }
}
