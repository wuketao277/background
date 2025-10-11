package com.hello.background.controller;

import com.hello.background.service.SchoolTypeService;
import com.hello.background.vo.SchoolTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuketao
 * @date 2025/10/10
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("schoolType")
public class SchoolTypeController {
    @Autowired
    private SchoolTypeService schoolTypeService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public SchoolTypeVO save(@RequestBody SchoolTypeVO vo) {
        return schoolTypeService.save(vo);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryPage")
    public Page<SchoolTypeVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        return schoolTypeService.queryPage(search, currentPage, pageSize);
    }

    /**
     * 通过id查询
     *
     * @param id
     */
    @GetMapping("queryById")
    public SchoolTypeVO queryById(Integer id) {
        return schoolTypeService.queryById(id);
    }

    /**
     * 检查是否为公立学校
     *
     * @param schoolName
     */
    @GetMapping("checkIsPublic")
    public String checkIsPublic(String schoolName) {
        return schoolTypeService.checkIsPublic(schoolName);
    }
}
