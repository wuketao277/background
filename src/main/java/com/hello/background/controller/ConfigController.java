package com.hello.background.controller;

import com.hello.background.service.ConfigService;
import com.hello.background.vo.ConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 配置 控制器
 *
 * @author wuketao
 * @date 2022/11/04
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 通过类别查询
     *
     * @param category
     * @return
     */
    @GetMapping("findAllByCategory")
    public List<ConfigVO> findAllByCategoryOrderById(String category) {
        return configService.findAllByCategoryOrderById(category);
    }

    /**
     * 通过类别和类型查询
     *
     * @param category
     * @return
     */
    @GetMapping("findAllByCategoryAndType")
    public List<ConfigVO> findAllByCategoryAndTypeOrderById(@RequestParam("category") String category, @RequestParam("type") String type) {
        return configService.findAllByCategoryAndTypeOrderById(category, type);
    }

    /**
     * 通过类别、类型、编码查询
     *
     * @param category
     * @return
     */
    @GetMapping("findFirstByCategoryAndTypeAndCode")
    public ConfigVO findFirstByCategoryAndTypeAndCodeOrderById(@RequestParam("category") String category, @RequestParam("type") String type, @RequestParam("code") String code) {
        return configService.findFirstByCategoryAndTypeAndCodeOrderById(category, type, code);
    }
}
