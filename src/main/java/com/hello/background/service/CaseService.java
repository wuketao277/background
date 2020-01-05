package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.ClientCase;
import com.hello.background.repository.CaseRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ClientCaseVO;
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
    public ClientCaseVO save(ClientCaseVO vo) {
        ClientCase c = TransferUtil.transferTo(vo, ClientCase.class);
        c = caseRepository.save(c);
        return TransferUtil.transferTo(c, ClientCaseVO.class);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<ClientCaseVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<ClientCase> casePage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            casePage = caseRepository.findAll(pageable);
            total = caseRepository.count();
        } else {
            casePage = caseRepository.findByTitleLike(search, pageable);
            total = caseRepository.countByTitleLike(search);
        }
        Page<ClientCaseVO> map = casePage.map(x -> TransferUtil.transferTo(x, ClientCaseVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }
}
