package com.hello.background.service;

import com.hello.background.domain.PRC;
import com.hello.background.repository.PRCRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.PRCVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Transactional
@Slf4j
@Service
public class PRCService {

    @Autowired
    private PRCRepository prcRepository;

    /**
     * 通过id，查询PRC信息
     *
     * @param id PRC主键
     * @return
     */
    public PRCVO findById(Integer id) {
        Optional<PRC> prcOptional = prcRepository.findById(id);
        if (prcOptional.isPresent()) {
            return TransferUtil.transferTo(prcOptional.get(), PRCVO.class);
        }
        return null;
    }

    /**
     * 保存候选人
     *
     * @param vo
     * @return
     */
    public PRCVO save(PRCVO vo) {
        PRC prc = TransferUtil.transferTo(vo, PRC.class);
        prc = prcRepository.save(prc);
        return TransferUtil.transferTo(prc, PRCVO.class);
    }


    /**
     * 通过id
     *
     * @param id
     */
    public void deleteById(Integer id) {
        prcRepository.deleteById(id);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<PRCVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Page<PRC> all = prcRepository.findAll(pageable);
        Page<PRCVO> map = all.map(x -> TransferUtil.transferTo(x, PRCVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }
}
