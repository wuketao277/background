package com.hello.background.service;

import com.hello.background.domain.Config;
import com.hello.background.repository.ConfigRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Transactional
@Slf4j
@Service
public class ConfigService {
    @Autowired
    private ConfigRepository configRepository;

    /**
     * 通过类别查询
     *
     * @param category
     * @return
     */
    public List<ConfigVO> findAllByCategoryOrderById(String category) {
        List<Config> list = configRepository.findAllByCategoryOrderById(category);
        return list.stream().map(x -> TransferUtil.transferTo(x, ConfigVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过类别和类型查询
     *
     * @param category
     * @return
     */
    public List<ConfigVO> findAllByCategoryAndTypeOrderById(String category, String type) {
        List<Config> list = configRepository.findAllByCategoryAndTypeOrderById(category, type);
        return list.stream().map(x -> TransferUtil.transferTo(x, ConfigVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过类别、类型、编码查询
     *
     * @param category
     * @return
     */
    public ConfigVO findFirstByCategoryAndTypeAndCodeOrderById(String category, String type, String code) {
        Config config = configRepository.findFirstByCategoryAndTypeAndCodeOrderById(category, type, code);
        return TransferUtil.transferTo(config, ConfigVO.class);
    }
}
