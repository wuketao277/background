package com.hello.background.controller;

import com.hello.background.service.CandidateClientRepeatedLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 候选人客户重复标签控制器
 *
 * @author wuketao
 * @date 2026/2/20
 * @Description
 */
@RestController
@RequestMapping("candidateClientRepeatedLabel")
public class CandidateClientRepeatedLabelController {
    @Autowired
    private CandidateClientRepeatedLabelService labelService;

    /**
     * 保存标签
     *
     * @param name
     */
    @GetMapping("save")
    public void save(String name) {
        labelService.save(name);
    }

    /**
     * 获取全部标签名称
     *
     * @return
     */
    @GetMapping("findAllName")
    public List<String> findAllName() {
        return labelService.findAll().stream().map(v -> v.getName()).collect(Collectors.toList());
    }

    /**
     * 通过名称删除标签
     *
     * @param name
     */
    @DeleteMapping("deleteByName")
    public void deleteByName(String name) {
        labelService.deleteByName(name);
    }

    /**
     * 先删除，然后获取全部标签名称
     *
     * @return
     */
    @GetMapping("deleteThenFindAllName")
    public List<String> deleteThenFindAllName(String name) {
        return labelService.deleteThenFindAllName(name).stream().map(v -> v.getName()).collect(Collectors.toList());
    }
}

