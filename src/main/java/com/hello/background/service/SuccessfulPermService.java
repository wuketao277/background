package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.SuccessfulPermVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@Transactional
@Service
public class SuccessfulPermService {

    @Autowired
    private SuccessfulPermRepository successfulPermRepository;

    public SuccessfulPermVO save(SuccessfulPermVO vo) {
        SuccessfulPerm successfulPerm = new SuccessfulPerm();
        BeanUtils.copyProperties(vo, successfulPerm);
        successfulPerm = successfulPermRepository.save(successfulPerm);
        vo.setId(successfulPerm.getId());
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
    public Page<SuccessfulPermVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "id");
        Page<SuccessfulPerm> successfulPermVOPage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            successfulPermVOPage = successfulPermRepository.findAll(pageable);
            total = successfulPermRepository.count();
        } else {
            successfulPermVOPage = successfulPermRepository.findByClientNameLikeOrCandidateChineseNameLikeOrConsultantRealNameLikeOrConsultantUserNameLikeOrConsultantRealName2LikeOrConsultantUserName2LikeOrConsultantRealName3LikeOrConsultantUserName3LikeOrConsultantRealName4LikeOrConsultantUserName4LikeOrConsultantRealName5LikeOrConsultantUserName5Like(search, search, search, search, search, search, search, search, search, search, search, search, pageable);
            total = successfulPermRepository.countByClientNameLikeOrCandidateChineseNameLikeOrConsultantRealNameLikeOrConsultantUserNameLikeOrConsultantRealName2LikeOrConsultantUserName2LikeOrConsultantRealName3LikeOrConsultantUserName3LikeOrConsultantRealName4LikeOrConsultantUserName4LikeOrConsultantRealName5LikeOrConsultantUserName5Like(search, search, search, search, search, search, search, search, search, search, search, search);
        }
        Page<SuccessfulPermVO> map = successfulPermVOPage.map(x -> TransferUtil.transferTo(x, SuccessfulPermVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }
}
