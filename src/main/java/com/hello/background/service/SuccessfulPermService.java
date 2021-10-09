package com.hello.background.service;

import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.vo.SuccessfulPermVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
}
