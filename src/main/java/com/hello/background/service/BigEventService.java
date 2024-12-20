package com.hello.background.service;

import com.hello.background.domain.BigEvent;
import com.hello.background.repository.BigEventRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.BigEventVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 大事件服务类
 */
@Transactional
@Slf4j
@Service
public class BigEventService {
    @Autowired
    private BigEventRepository bigEventRepository;

    /**
     * 保存
     *
     * @param vo
     * @param user
     * @return
     */
    public BigEventVO save(BigEventVO vo, UserVO user) {
        if (null == vo.getId()) {
            // 新增
            vo.setCreateDateTime(LocalDateTime.now());
            vo.setCreateUserName(user.getUsername());
            vo.setCreateRealName(user.getRealname());
            BigEvent bigEvent = bigEventRepository.save(TransferUtil.transferTo(vo, BigEvent.class));
            return TransferUtil.transferTo(bigEvent, BigEventVO.class);
        } else {
            // 修改
            BigEvent bigEvent = bigEventRepository.findById(vo.getId()).get();
            bigEvent.setTitle(vo.getTitle());
            bigEvent.setDetail(vo.getDetail());
            bigEvent = bigEventRepository.save(bigEvent);
            return TransferUtil.transferTo(bigEvent, BigEventVO.class);
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        bigEventRepository.deleteById(id);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public BigEventVO findById(Integer id) {
        return TransferUtil.transferTo(bigEventRepository.findById(id).get(), BigEventVO.class);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<BigEventVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        List<Sort.Order> orderList = new ArrayList<>();
        orderList.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, new Sort(orderList));
        search = "%" + search + "%";
        Page<BigEvent> page = bigEventRepository.findByTitleLikeOrDetailLike(search, search, pageable);
        long total = bigEventRepository.countByTitleLikeOrDetailLike(search, search);
        Page<BigEventVO> map = new PageImpl<>(page.getContent().stream().map(x -> TransferUtil.transferTo(x, BigEventVO.class)).collect(Collectors.toList()), new PageRequest(page.getPageable().getPageNumber(), page.getPageable().getPageSize()), total);
        return map;
    }
}
